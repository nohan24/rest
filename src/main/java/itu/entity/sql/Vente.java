package itu.entity.sql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voiture_generator")
    @SequenceGenerator(name = "voiture_generator", sequenceName = "vente_seq", allocationSize = 1)
    private Integer id;
    double commission;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    Utilisateur utilisateur;
    @OneToOne
    @JoinColumn(name = "voiture_id")
    Voiture voiture;
    @CreationTimestamp
    Date date_vente;
}
