package itu.repository.nosql;

import itu.entity.nosql.ModeleBase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ModeleRepo extends MongoRepository<ModeleBase, String> {
    boolean existsByMarqueAndModele(String marque, String modele);
    List<ModeleBase> findAllByMarque(String marque);
}
