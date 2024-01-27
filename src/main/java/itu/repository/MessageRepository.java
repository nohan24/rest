package itu.repository;

import itu.entity.sql.Chat;
import itu.entity.sql.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByIdChat(Chat idChat);

}
