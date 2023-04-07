package br.com.shapeup.adapters.output.repository.mongo.post.comment;

import br.com.shapeup.adapters.output.repository.model.post.comments.AnswerCommentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface AnswerCommentMongoRepository extends MongoRepository<AnswerCommentEntity, UUID> {
}
