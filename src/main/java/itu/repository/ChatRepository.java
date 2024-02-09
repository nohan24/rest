package itu.repository;

import itu.entity.sql.Chat;
import itu.entity.sql.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    List<Chat> findByFirstUserIdOrSecondUserIdOrdOrderByLastSentDesc(Utilisateur firstUser, Utilisateur secondUser);

    Optional<Chat> findByFirstUserIdAndSecondUserId(Utilisateur firstUser, Utilisateur secondUser);

    Optional<Chat> findById(Integer idChat);
    Chat save(Chat cha);
}
