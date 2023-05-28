package br.com.shapeup.adapters.output.repository.mongo.post;

import br.com.shapeup.adapters.output.repository.model.post.post.PostLikeEntity;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostLikeMongoRepository extends MongoRepository<PostLikeEntity, UUID> {
    Integer countAllByPostId(String postId);

    boolean existsByPostIdAndUserId(String postId, String id);

    void deleteByUserIdAndPostId(String value, String postId);

    void deleteAllByUserId(String userId);
}
