package itu.entity.sql;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voiture_generator")
    @SequenceGenerator(name = "voiture_generator", sequenceName = "voiture_seq", allocationSize = 1)
    private Integer id;
    @Column(nullable = false)
    private String caracteristiqueID;
    @CreationTimestamp
    private Date dateCreation;
    @CreationTimestamp
    private Date dateValidation;
    @Column(nullable = false)
    private double prix;
    private int etat = 100;

    public String getCaracteristiqueID() {
        return caracteristiqueID;
    }

    public void setCaracteristiqueID(String caracteristiqueID) {
        this.caracteristiqueID = caracteristiqueID;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(Date dateValidation) {
        this.dateValidation = dateValidation;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
