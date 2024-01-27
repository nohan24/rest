package itu.repository;

import itu.entity.sql.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobileRepo extends JpaRepository<Mobile, Integer> {
    void deleteByToken(String token);
    List<Mobile> findAllByUserid(Integer id);
}
