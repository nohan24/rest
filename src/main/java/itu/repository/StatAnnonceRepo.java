package itu.repository;

import itu.entity.sql.StatAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatAnnonceRepo extends JpaRepository<StatAnnonce, Integer> {

}
