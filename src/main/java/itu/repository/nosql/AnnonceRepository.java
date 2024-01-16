package itu.repository.nosql;

import itu.entity.nosql.Annonce;
import itu.entity.nosql.Detail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnnonceRepository extends MongoRepository<Annonce, String> {
}
