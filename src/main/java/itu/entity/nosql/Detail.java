package itu.entity.nosql;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "detail")
public class Detail {
    @Id
    private String _id;
    private String marque;
    private Modele modele;
    private String categorie;
    private int kilometrage;
    private int puissance_fiscale;
    private int puissance_reelle;
    private String transmission;
    private String carburant;
    private int annee_fabrication;
    private Detailelectrique detailelectrique;
    private String description_supplementaire;
    private String[] equipement;
    private String path_image_couverture;
}
