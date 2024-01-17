package itu.entity.nosql;


public class Detail {
    private String marque;
    private String modele;
    private String categorie;
    private Integer kilometrage;
    private Integer puissance_fiscale;
    private Integer puissance_reelle;
    private String transmission;
    private String carburant;
    private Integer annee_fabrication;
    private String titre_voiture;

    public String getTitre_voiture() {
        return titre_voiture;
    }

    public void setTitre_voiture(String titre_voiture) {
        this.titre_voiture = titre_voiture;
    }

    private String description_supplementaire;
    private Integer portes = null;
    private Integer places = null;
    private String[] equipement;

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) throws Exception {
        if(marque == null || marque == "")throw new Exception("Une marque est requise.");
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) throws Exception {
        if(modele == "" ||modele == null)throw new Exception("Modèle requis.");
        this.modele = modele;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) throws Exception {
        if(categorie == "" || categorie == null) throw new Exception("Catégorie requis.");
        this.categorie = categorie;
    }

    public Integer getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(Integer kilometrage) {
        this.kilometrage = kilometrage;
    }

    public Integer getPuissance_fiscale() {
        return puissance_fiscale;
    }

    public void setPuissance_fiscale(Integer puissance_fiscale) {
        this.puissance_fiscale = puissance_fiscale;
    }

    public Integer getPuissance_reelle() {
        return puissance_reelle;
    }

    public void setPuissance_reelle(Integer puissance_reelle) {
        this.puissance_reelle = puissance_reelle;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) throws Exception {
        if(transmission == null) throw new Exception("Transmission requise.");
        this.transmission = transmission;
    }

    public String getCarburant() {
        return carburant;
    }

    public void setCarburant(String carburant) throws Exception {
        if(carburant == null) throw new Exception("Carburant requis.");
        this.carburant = carburant;
    }

    public Integer getAnnee_fabrication() {
        return annee_fabrication;
    }

    public void setAnnee_fabrication(Integer annee_fabrication) {
        this.annee_fabrication = annee_fabrication;
    }

    public String getDescription_supplementaire() {
        return description_supplementaire;
    }

    public void setDescription_supplementaire(String description_supplementaire) {
        this.description_supplementaire = description_supplementaire;
    }

    public Integer getPortes() {
        return portes;
    }

    public void setPortes(Integer portes) {
        this.portes = portes;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public String[] getEquipement() {
        return equipement;
    }

    public void setEquipement(String[] equipement) throws Exception {
        if(equipement.length == 0 || equipement == null) throw new Exception("Préciser les équipements du véhicule.");
        this.equipement = equipement;
    }
}
