package itu.services;

import itu.DTO.MessageDTO;
import itu.exception.MyCustomException;
import itu.entity.sql.Chat;
import itu.entity.sql.Message;
import itu.entity.sql.Utilisateur;
import itu.repository.ChatRepository;
import itu.repository.MessageRepository;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    private final UtilisateurService userService;

    private final ChatService chatService;

    private final ChatRepository chatRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UtilisateurService userService, ChatService chatService, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.chatService = chatService;
        this.chatRepository = chatRepository;
    }

    @Override
    public Message addMessage(MessageDTO messageDTO) {
        List<Integer> listUser = userService.listUserAsc(messageDTO.getSenderId(), messageDTO.getReceiverId());
        Utilisateur firstUser = userService.getUserById(listUser.get(0));
        Utilisateur secondUser = userService.getUserById(listUser.get(1));
        Utilisateur sender = userService.getUserById(messageDTO.getSenderId());
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if(chatRepository.findByFirstUserIdAndSecondUserId(firstUser, secondUser).isEmpty()) {
            Chat myChat = new Chat(firstUser, secondUser);
            chatService.createChat(myChat);

            Message message = new Message(sender, time, messageDTO.getMessageContent(),myChat);
            return messageRepository.save(message);
        } else if(chatRepository.findByFirstUserIdAndSecondUserId(firstUser, secondUser).isPresent()){
            Chat getChat = chatRepository.findByFirstUserIdAndSecondUserId(firstUser, secondUser).get();
            Message message = new Message(sender, time, messageDTO.getMessageContent(), getChat);
            return messageRepository.save(message);
        } else {
            throw new MyCustomException("Erreur interne du serveur veuillez reessayer");
        }
    }

    public List<Message> getListMessageByIdChat(Integer senderId, Integer receiverId) {
        List<Integer> listUser = userService.listUserAsc(senderId, receiverId);
        Utilisateur firstUser = userService.getUserById(listUser.get(0));
        Utilisateur secondUser = userService.getUserById(listUser.get(1));
        Chat chat = chatRepository.findByFirstUserIdAndSecondUserId(firstUser, secondUser).get();
        List<Message> listMessage = messageRepository.findByIdChat(chat);
        return listMessage;
    }
}
