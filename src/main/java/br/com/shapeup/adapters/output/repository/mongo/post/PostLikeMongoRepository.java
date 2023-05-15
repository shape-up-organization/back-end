package br.com.shapeup.adapters.output.repository.mongo.post;

import br.com.shapeup.adapters.output.repository.model.post.post.PostLikeEntity;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostLikeMongoRepository extends MongoRepository<PostLikeEntity, UUID> {
    Integer countAllByPostId(String postId);

    boolean existsByUserId(String userId);

    void deleteByUserId(String userId);

    boolean existsByPostIdAndUserId(String postId, String id);
}
