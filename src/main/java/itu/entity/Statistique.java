package itu.entity;

import itu.entity.sql.StatAnnonce;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class Statistique {
    int utilisateur;
    int annonce;
    int vente;
    double annonce_vente;
    List<StatAnnonce> annonces;
    double totalprix;
}
