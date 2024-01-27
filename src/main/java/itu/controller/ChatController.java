package itu.controller;

import itu.entity.sql.Chat;
import itu.repository.ChatRepository;
import itu.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
public class ChatController {

    private final ChatRepository chatRepository;
    private final ChatService chatService;

    public ChatController(ChatRepository chatRepository, ChatService chatService) {
        this.chatRepository = chatRepository;
        this.chatService = chatService;
    }

    @PostMapping("/chats")
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) throws IOException {
        try {
            return new ResponseEntity<Chat>(chatService.createChat(chat), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("error while adding a new chat", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/chats")
    public ResponseEntity<List<Chat>> getChatByUserId() throws IOException {
        try {
            return new ResponseEntity<List<Chat>>(chatService.getChatByUserId((Integer) SecurityContextHolder.getContext().getAuthentication().getCredentials()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("can`t find any chat", HttpStatus.NOT_FOUND);
        }
    }
}
