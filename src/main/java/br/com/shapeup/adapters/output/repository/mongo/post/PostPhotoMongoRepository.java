package br.com.shapeup.adapters.output.repository.mongo.post;

import br.com.shapeup.adapters.output.repository.model.post.post.PostPhotoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PostPhotoMongoRepository extends MongoRepository<PostPhotoEntity, UUID> {
}
