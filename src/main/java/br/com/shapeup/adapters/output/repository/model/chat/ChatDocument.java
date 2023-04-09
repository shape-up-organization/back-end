package br.com.shapeup.adapters.output.repository.model.chat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDocument {

    @MongoId(FieldType.OBJECT_ID)
    private UUID id;
    private String senderName;
    private String receiverName;
    private List<Message> message;
    private final LocalDateTime date = LocalDateTime.now();
}
