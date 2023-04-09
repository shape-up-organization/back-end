package br.com.shapeup.adapters.output.repository.jpa.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipMongoRepository extends MongoRepository<FriendshipRequestDocument, String> {
    Boolean existsByUsernameSenderAndUsernameReceiver(String usernameSender, String usernameReceiver);
    FriendshipRequestDocument findByUsernameSenderAndUsernameReceiver(String usernameSender, String usernameReceiver);
}
