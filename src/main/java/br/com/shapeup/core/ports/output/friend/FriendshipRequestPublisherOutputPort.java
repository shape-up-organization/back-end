package br.com.shapeup.core.ports.output.friend;

import br.com.shapeup.core.messages.FriendshipRequestMessage;

public interface FriendshipRequestPublisherOutputPort {
    void send(FriendshipRequestMessage message);
}
