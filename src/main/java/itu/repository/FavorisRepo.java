package itu.repository;

import itu.entity.sql.Favoris;
import itu.entity.sql.Utilisateur;
import itu.entity.sql.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavorisRepo extends JpaRepository<Favoris, Integer> {
    List<Favoris> findAllByUtilisateur(Utilisateur utilisateur);
    boolean existsByUtilisateurAndVoiture(Utilisateur u, Voiture v);
    void deleteByUtilisateurAndVoiture(Utilisateur u, Voiture v);
}
