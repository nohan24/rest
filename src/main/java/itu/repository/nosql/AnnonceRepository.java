package itu.repository.nosql;

import itu.entity.nosql.Detail_annonce;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnnonceRepository extends MongoRepository<Detail_annonce, String> {
}
