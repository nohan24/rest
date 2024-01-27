package itu.repository;

import itu.entity.sql.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Integer> {
    List<Voiture> findAllByEtatOrderByDateCreationDesc(int etat);
    List<Voiture> findAllByOwnerIsNotAndEtat(int id, int etat);
    List<Voiture> findAllByOwner(int id);
    Voiture findByCaracteristiqueID(String c);
    @Query("SELECT count(*) FROM Voiture where etat = 200")
    int countByEtat();

    List<Voiture> findAllByPrixBetween(int min, long max);

}
