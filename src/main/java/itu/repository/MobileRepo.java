package itu.repository;

import itu.entity.sql.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileRepo extends JpaRepository<Mobile, Integer> {
    void deleteByToken(String token);
}
