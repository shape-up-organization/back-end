package br.com.shapeup.adapters.output.repository.mongo.post.comment;

import br.com.shapeup.adapters.output.repository.model.post.comments.CommentEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostCommentMongoRepository extends MongoRepository<CommentEntity, UUID> {
    Integer countAllByIdPost(String postId);

    boolean existsByUserIdAndId(String userId, UUID commentId);

    Page<CommentEntity> findAllByIdPostOrderByCreatedAt(String postId, PageRequest pageRequest);
}
