package br.com.shapeup.adapters.output.repository.mongo.level;

import br.com.shapeup.adapters.output.repository.model.level.LevelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LevelMongoRepository extends MongoRepository<LevelEntity, Long> {
}
