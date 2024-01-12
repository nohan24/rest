package itu.repository.nosql;

import itu.entity.nosql.Categorie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategorieRepo extends MongoRepository<Categorie, String> {
}
