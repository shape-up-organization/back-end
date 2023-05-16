package br.com.shapeup.adapters.output.repository.mongo.post.comment;

import br.com.shapeup.adapters.output.repository.model.post.comments.AnswerCommentEntity;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerCommentMongoRepository extends MongoRepository<AnswerCommentEntity, UUID> {
}
