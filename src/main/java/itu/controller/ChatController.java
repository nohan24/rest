package itu.controller;

import itu.entity.sql.Chat;
import itu.entity.sql.Utilisateur;
import itu.repository.ChatRepository;
import itu.repository.UtilisateurRepository;
import itu.services.ChatService;
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
    private final UtilisateurRepository utilisateurRepository;

    public ChatController(ChatRepository chatRepository, ChatService chatService, UtilisateurRepository utilisateurRepository) {
        this.chatRepository = chatRepository;
        this.chatService = chatService;
        this.utilisateurRepository = utilisateurRepository;
    }

    @PostMapping("/chats")
    public ResponseEntity<Chat> createChat(@RequestBody Chat chat) throws IOException {
        try {
            Chat c = new Chat(chat.getFirstUserId(), utilisateurRepository.findById ((Integer)SecurityContextHolder.getContext().getAuthentication().getCredentials()).get());
            return new ResponseEntity<Chat>(chatService.createChat(c), HttpStatus.OK);
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
