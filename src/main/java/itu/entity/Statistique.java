package itu.entity;

public class Statistique {
    long utilisateur;
    long annonce;
    int vente;
    double annonce_vente;

    public long getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(long utilisateur) {
        this.utilisateur = utilisateur;
    }

    public long getAnnonce() {
        return annonce;
    }

    public void setAnnonce(long annonce) {
        this.annonce = annonce;
    }

    public int getVente() {
        return vente;
    }

    public void setVente(int vente) {
        this.vente = vente;
    }

    public double getAnnonce_vente() {
        return annonce_vente;
    }

    public void setAnnonce_vente(double annonce_vente) {
        this.annonce_vente = annonce_vente;
    }
}
