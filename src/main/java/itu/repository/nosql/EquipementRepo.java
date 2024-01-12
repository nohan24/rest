package itu.repository.nosql;

import itu.entity.nosql.Equipement;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EquipementRepo extends MongoRepository<Equipement, String> {
}
