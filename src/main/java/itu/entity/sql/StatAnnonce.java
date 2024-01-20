package itu.entity.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "statannonce")
@Getter
@Setter
public class StatAnnonce {
    @Id
    private Integer mois;
    String designation;
    int total;
}
