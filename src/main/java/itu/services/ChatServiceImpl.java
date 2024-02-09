package itu.services;

import itu.entity.sql.Chat;
import itu.entity.sql.Utilisateur;
import itu.repository.ChatRepository;
import itu.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl  implements ChatService {

    private final ChatRepository chatRepository;

    private final UtilisateurService userService;

    public ChatServiceImpl(ChatRepository chatRepository, UtilisateurService userService) {
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    public List<Chat> getChatByUserId(Integer user) {
        Utilisateur firstUser = userService.getUserById(user);

        if(chatRepository.findByFirstUserIdOrSecondUserIdOrdOrderByLastSentDesc(firstUser, firstUser).isEmpty() ) {
            throw new EntityNotFoundException("Pas encore de chat existant");
        } else {
            return chatRepository.findByFirstUserIdOrSecondUserIdOrdOrderByLastSentDesc(firstUser, firstUser);
        }
    }

    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
