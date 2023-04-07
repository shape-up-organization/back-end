package br.com.shapeup.adapters.output.repository.mongo.storie;

import br.com.shapeup.adapters.output.repository.model.storie.StoriesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface StorieMongoRepository extends MongoRepository<StoriesEntity, UUID> {
}
