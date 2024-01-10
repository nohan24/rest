package itu.repository;

import itu.entity.sql.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Admin, Integer> {
    Admin findByEmail(String email);
}
