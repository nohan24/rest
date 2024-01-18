package itu.entity.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Vente {
    @Id
    private Integer id;
    double commission;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
