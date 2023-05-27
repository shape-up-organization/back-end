package br.com.shapeup.adapters.output.repository.mongo.post.comment;

import br.com.shapeup.adapters.output.repository.model.post.comments.CommentEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentMongoRepository extends JpaRepository<CommentEntity, UUID> {
    Integer countAllByIdPost(String postId);

    boolean existsByUserIdAndId(String userId, UUID commentId);

    Page<CommentEntity> findAllByIdPostOrderByCreatedAt(String postId, PageRequest pageRequest);

    void deleteAllByIdPost(String postId);
}
