package itu.repository;

import itu.entity.sql.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Integer> {
    List<Voiture> findAllByEtat(int etat);
    List<Voiture> findAllByOwnerIsNotAndEtat(int id, int etat);
    List<Voiture> findAllByOwner(int id);
}
