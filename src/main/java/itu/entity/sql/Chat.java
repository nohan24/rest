package itu.entity.sql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column (name = "idChat")
    private Integer idChat;
    private String last_message = "";
    private LocalDateTime lastSent = LocalDateTime.now();;
    @ManyToOne
    @JoinColumn(name="firstUserId", referencedColumnName = "id_utilisateur")
    private Utilisateur firstUserId;

    @ManyToOne
    @JoinColumn(name="SecondUserId", referencedColumnName = "id_utilisateur")
    private Utilisateur secondUserId;


    public Chat(Utilisateur firstUserId, Utilisateur secondUserId) {
        if(firstUserId.getId_utilisateur() < secondUserId.getId_utilisateur()) {
            this.setFirstUserId(firstUserId);
            this.setSecondUserId(secondUserId);
        } else {
            this.setFirstUserId(secondUserId);
            this.setSecondUserId(firstUserId);
        }
    }
}
