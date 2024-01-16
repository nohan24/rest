package itu.entity;

import itu.entity.nosql.Detail_annonce;
import itu.entity.sql.Voiture;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Annonce {
    private Voiture voiture;
    private Detail_annonce detailAnnonce;
}
