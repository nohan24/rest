package itu.controller;

import itu.DTO.MessageDTO;
import itu.entity.sql.Message;
import itu.repository.MessageRepository;
import itu.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class MessageController {
    private final MessageRepository messageRepository;

    private final MessageService messageService;

    public MessageController(MessageRepository messageRepository, MessageService messageService) {
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> addMessage(@RequestBody MessageDTO messageDTO) throws IOException {
        try {
            return new ResponseEntity<Message>(messageService.addMessage(messageDTO), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<List<Message>> getListMessageByIdChat(@PathVariable(name = "id")int senderId) throws Exception {
        try {
            return new ResponseEntity<List<Message>>(messageService.getListMessageByIdChat(senderId, (Integer) SecurityContextHolder.getContext().getAuthentication().getCredentials()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.CONFLICT);
        }
    }
}
