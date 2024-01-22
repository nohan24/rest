package itu.entity.sql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Favoris {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myseq")
    @SequenceGenerator(name="myseq",sequenceName="favoris_seq", allocationSize = 1)
    private int id;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;
    @ManyToOne
    @JoinColumn(name = "voiture_id")
    private Voiture voiture;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
