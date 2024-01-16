package itu.services;

import itu.entity.sql.Utilisateur;
import itu.repository.UtilisateurRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RegisterServices {
    private final UtilisateurRepository utilisateurRepository;

    public RegisterServices(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    public Utilisateur register(String email, String password, String date_naissance, int sexe, String username){
        Utilisateur u = new Utilisateur();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        u.setEmail(email);
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setDate_naissance(new Date(date_naissance));
        u.setGenre(sexe);
        u.setUsername(username);
        return utilisateurRepository.save(u);
    }
}
