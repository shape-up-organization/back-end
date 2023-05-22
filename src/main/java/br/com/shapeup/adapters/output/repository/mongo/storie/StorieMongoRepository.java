package br.com.shapeup.adapters.output.repository.mongo.storie;

import br.com.shapeup.adapters.output.repository.model.storie.StoriesEntity;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StorieMongoRepository extends MongoRepository<StoriesEntity, UUID> {
}
