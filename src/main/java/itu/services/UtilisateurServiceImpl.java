package itu.services;

import itu.entity.sql.Utilisateur;
import itu.repository.UtilisateurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{
    private final UtilisateurRepository userRepo;

    public UtilisateurServiceImpl(UtilisateurRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Utilisateur getUserById(Integer userId) {
       return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId));
    }

    public Utilisateur addUser(Utilisateur utilisateur) {
       return userRepo.save(utilisateur);
    }

    public List<Integer> listUserAsc(Integer firstUserId, Integer secondUserId) {
       List<Integer> list = new ArrayList<>();
       if(firstUserId < secondUserId) {
           list.add(firstUserId);
           list.add(secondUserId);
       } else {
           list.add(secondUserId);
           list.add(firstUserId);
       }
       return list;
    }
}
