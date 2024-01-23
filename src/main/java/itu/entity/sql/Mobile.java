package itu.entity.sql;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Mobile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voiture_generator")
    @SequenceGenerator(name = "voiture_generator", sequenceName = "token_seq", allocationSize = 1)
    private Integer id;
    private Integer userid;
    private String token;
}
