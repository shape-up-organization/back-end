package br.com.shapeup.adapters.output.integration.chat;

import br.com.shapeup.adapters.output.repository.model.chat.ChatDocument;
import br.com.shapeup.adapters.output.repository.model.chat.Message;
import br.com.shapeup.adapters.output.repository.mongo.chat.ChatMongoRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatAdapter implements ChatGateway {

    private final ChatMongoRepository chatMongoRepository;

    @Override
    public void saveMessageToDatabase(Message message) {
        var chatDocumentOptional = chatMongoRepository.findChatDocumentBySenderNameAndReceiverName(
                message.getSenderName(),
                message.getReceiverName()
        );

        checkIfChatExistsAndSaveMessageInDatabase(message, chatDocumentOptional);
    }

    private void checkIfChatExistsAndSaveMessageInDatabase(
            Message message,
            Optional<ChatDocument> chatDocumentOptional
    ) {
        chatDocumentOptional.ifPresentOrElse(chat -> {
            chat.getMessage().add(message);
            chatMongoRepository.save(chat);
        }, () -> {
            var chatDocument = ChatDocument.builder()
                    .senderName(message.getSenderName())
                    .receiverName(message.getReceiverName())
                    .message(List.of(message))
                    .build();
            chatMongoRepository.save(chatDocument);
        });
    }
}
