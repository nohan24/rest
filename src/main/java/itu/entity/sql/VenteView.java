package itu.entity.sql;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "v_vente")
@Setter
@Getter
public class VenteView {
    @Id
    int id;
    double commission;
    int month;
    String designation;
}
