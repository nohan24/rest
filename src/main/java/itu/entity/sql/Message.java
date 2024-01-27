package itu.entity.sql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "message")
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idMessage")
    private Integer idMessage;

    @ManyToOne
    @JoinColumn(name="idUtilisateur", referencedColumnName = "id_utilisateur")
    private Utilisateur utilisateur;

    private Timestamp dateTime;
    private String messageContent;

    @ManyToOne
    @JoinColumn(name="idChat", referencedColumnName = "idChat")
    private Chat idChat;


    public Message(Utilisateur sender, Timestamp time, String messageContent, Chat myChat) {
        this.setUtilisateur(sender);
        this.setDateTime(time);
        this.setMessageContent(messageContent);
        this.setIdChat(myChat);
    }
}
