package itu.repository;

import itu.entity.nosql.Detail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetailRepository extends MongoRepository<Detail, String> {
}
