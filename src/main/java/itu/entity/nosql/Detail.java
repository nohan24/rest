package itu.entity.nosql;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Detail {
    private String marque;
    private String modele;
    private String categorie;
    private int kilometrage;
    private int puissance_fiscale;
    private int puissance_reelle;
    private String transmission;
    private String carburant;
    private int annee_fabrication;
    private String description_supplementaire;
    private int portes;
    private int places;
    private String[] equipement;

}
