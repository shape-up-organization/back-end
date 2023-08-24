package br.com.shapeup.core.messages;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record FriendshipRequestMessage(
        String id,
        String userSenderId,
        String userReceiverId,
        String usernameSender,
        String usernameReceiver,
        LocalDateTime createdAt
) {
}
