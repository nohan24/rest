package itu.repository.nosql;

import itu.entity.nosql.Carburant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarburantRepo extends MongoRepository<Carburant, String> {
}
