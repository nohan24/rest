package itu.repository;

import itu.entity.sql.Commission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionRepo extends JpaRepository<Commission, Long> {
}
