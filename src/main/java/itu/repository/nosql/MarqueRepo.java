package itu.repository.nosql;

import itu.entity.nosql.Marque;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarqueRepo extends MongoRepository<Marque, String> {
}
