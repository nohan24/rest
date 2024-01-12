package itu.repository.nosql;

import itu.entity.nosql.ModeleBase;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ModeleRepo extends MongoRepository<ModeleBase, String> {
}
