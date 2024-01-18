package itu.repository;

import itu.entity.sql.Favoris;
import itu.entity.sql.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavorisRepo extends JpaRepository<Favoris, Long> {
    List<Favoris> findAllByUtilisateur(Utilisateur utilisateur);
}
