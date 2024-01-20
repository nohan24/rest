package itu.repository;

import itu.entity.sql.StatAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatAnnonceRepo extends JpaRepository<StatAnnonce, Integer> {
}
