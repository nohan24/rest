package itu.services;

import itu.entity.sql.Utilisateur;

import java.util.List;


public interface UtilisateurService {
    Utilisateur getUserById(Integer userId);
    Utilisateur addUser(Utilisateur utilisateur);
    List<Integer> listUserAsc(Integer firstUserId, Integer secondUserId);
}
