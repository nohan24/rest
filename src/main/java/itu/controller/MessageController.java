package itu.controller;

import itu.DTO.MessageDTO;
import itu.entity.PushNotificationRequest;
import itu.entity.sql.Chat;
import itu.entity.sql.Message;
import itu.entity.sql.Mobile;
import itu.repository.ChatRepository;
import itu.repository.MessageRepository;
import itu.repository.MobileRepo;
import itu.services.MessageService;
import itu.services.PushNotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class MessageController {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final MobileRepo mobileRepo;
    private final MessageService messageService;
    private final PushNotificationService pushNotificationService;


    public MessageController(MessageRepository messageRepository, ChatRepository chatRepository, MobileRepo mobileRepo, MessageService messageService, PushNotificationService pushNotificationService) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.mobileRepo = mobileRepo;
        this.messageService = messageService;
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/messages")
    public ResponseEntity addMessage(@RequestBody MessageDTO messageDTO) throws IOException {
        try {
            messageDTO.setSenderId((Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials());
            Message m = messageService.addMessage(messageDTO);
            PushNotificationRequest request = new PushNotificationRequest();
            request.setMessage(m.getMessageContent());
            request.setTitle(m.getUtilisateur().getUsername() + " vous a envoyé un message.");
            request.setTopic("common");
            List<Mobile> ms = mobileRepo.findAllByUserid(messageDTO.getReceiverId());
            if(!ms.isEmpty()){
                for(Mobile mobile : ms){
                    request.setToken(mobile.getToken());
                    pushNotificationService.sendPushNotificationToToken(request);
                }
            }
            Chat c = m.getIdChat();
            c.setLastSent(LocalDateTime.now());
            c.setLast_message(m.getMessageContent());
            chatRepository.save(c);
            return ResponseEntity.ok(m);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/messages/chats/{id}")
    public ResponseEntity<List<Message>> getListMessageByIdChat(@PathVariable(name = "id")int senderId) throws Exception {
        try {
            return new ResponseEntity<List<Message>>(messageService.getListMessageByIdChat(senderId, (Integer) SecurityContextHolder.getContext().getAuthentication().getCredentials()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


}
