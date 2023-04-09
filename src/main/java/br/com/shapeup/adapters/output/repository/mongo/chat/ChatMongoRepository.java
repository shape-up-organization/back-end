package br.com.shapeup.adapters.output.repository.mongo.chat;

import br.com.shapeup.adapters.output.repository.model.chat.ChatDocument;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMongoRepository extends MongoRepository<ChatDocument, String> {
    Optional<ChatDocument> findChatDocumentBySenderNameAndReceiverName(
            String senderName,
            String receiverName
    );
}
