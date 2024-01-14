package itu.entity.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private double valeur;

    public double getValeur() {
        return valeur;
    }

    public void setValeur(double valeur) throws Exception {
        if(valeur <= 0)throw new Exception("La valeur n'est pas acceptable.");
        this.valeur = valeur;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
