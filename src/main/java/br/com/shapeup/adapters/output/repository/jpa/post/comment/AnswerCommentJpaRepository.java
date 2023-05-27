package br.com.shapeup.adapters.output.repository.jpa.post.comment;

import br.com.shapeup.adapters.output.repository.model.post.comments.AnswerCommentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerCommentJpaRepository extends JpaRepository<AnswerCommentEntity, UUID> {
}
