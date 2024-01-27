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

    private final UtilisateurRepository utilisateurRepository;

    public ChatServiceImpl(ChatRepository chatRepository, UtilisateurRepository utilisateurRepository) {
        this.chatRepository = chatRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    public List<Chat> getChatByUserId(Integer user) {
        Utilisateur u = utilisateurRepository.findById(user).get();

        if(chatRepository.findByFirstUserIdOrSecondUserId(u, u).isEmpty() ) {
            throw new EntityNotFoundException("Pas encore de chat existant");
        } else {
            return chatRepository.findByFirstUserIdOrSecondUserId(u, u);
        }
    }

    public Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
