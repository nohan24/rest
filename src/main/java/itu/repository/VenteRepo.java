package itu.repository;

import itu.entity.sql.Vente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenteRepo extends JpaRepository<Vente, Integer> {
}
