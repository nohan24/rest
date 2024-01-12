package itu.repository.nosql;

import itu.entity.nosql.Transmission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransmissionRepo extends MongoRepository<Transmission, String> {
}
