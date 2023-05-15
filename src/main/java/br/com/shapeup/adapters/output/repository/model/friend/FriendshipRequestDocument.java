package br.com.shapeup.adapters.output.repository.model.friend;

import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


import static java.util.UUID.randomUUID;

@Document("friendship_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipRequestDocument {
    @Id
    private String id = randomUUID().toString();
    private String usernameSender;
    private String usernameReceiver;
    private Boolean accepted;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public FriendshipRequestDocument(String usernameSender, String usernameReceiver) {
        this.usernameSender = usernameSender;
        this.usernameReceiver = usernameReceiver;
        this.accepted = false;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

}
