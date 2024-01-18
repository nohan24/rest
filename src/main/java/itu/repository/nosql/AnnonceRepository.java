package itu.repository.nosql;

import itu.entity.nosql.Detail_annonce;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnnonceRepository extends MongoRepository<Detail_annonce, String> {
    List<Detail_annonce> findAllByMarqueLikeIgnoreCaseAndModeleLikeIgnoreCaseAndKilometrageBetweenAndPlacesGreaterThanEqualAndCategorieInAndCarburantInAndTransmissionIn(String marque, String modele, int min, int max, int place,String[] categorie, String[] carburant, String[] transmission);
}
