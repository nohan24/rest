package itu.entity;

import itu.entity.nosql.Carburant;
import itu.entity.nosql.Categorie;
import itu.entity.nosql.Transmission;
import itu.repository.nosql.CarburantRepo;
import itu.repository.nosql.CategorieRepo;
import itu.repository.nosql.TransmissionRepo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
public class Recherche {
    public Recherche(){}
    public Recherche(CarburantRepo carburantRepo, CategorieRepo categorieRepo, TransmissionRepo transmissionRepo){
        List<Carburant> carburants = carburantRepo.findAll();
        carburant = new String[carburants.size()];
        for(int i = 0; i < carburants.size(); i++){
            carburant[i] = carburants.get(i).getCarburant();
        }

        List<Categorie> categories = categorieRepo.findAll();
        categorie = new String[categories.size()];
        for(int i = 0; i < categories.size(); i++){
            categorie[i] = categories.get(i).getCategorie();
        }

        List<Transmission> transmissions = transmissionRepo.findAll();
        transmission = new String[transmissions.size()];
        for(int i = 0; i < transmissions.size(); i++){
            transmission[i] = transmissions.get(i).getTransmission();
        }
    }

    String marque = "";
    String[] transmission = null;
    String modele = "";
    String[] categorie = null;
    String[] carburant = null;
    Integer prixmin = 0;
    Integer kmin = 0, kmax = 1000000;
    Integer place = 0;
    long prixmax = 1000000000;
}
