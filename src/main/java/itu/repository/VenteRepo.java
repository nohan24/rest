package itu.repository;

import itu.entity.sql.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VenteRepo extends JpaRepository<Vente, Integer> {
    @Query("select sum(v.commission) from Vente v")
    double sum();
}
