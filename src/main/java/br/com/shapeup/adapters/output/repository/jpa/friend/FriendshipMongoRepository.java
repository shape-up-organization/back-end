package br.com.shapeup.adapters.output.repository.jpa.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipMongoRepository extends MongoRepository<FriendshipRequestDocument, String> {

    void deleteByUsernameSenderAndUsernameReceiver(String usernameSender,String usernameReceiver);

    Optional<FriendshipRequestDocument> findByUsernameSenderAndUsernameReceiver(String usernameSender, String usernameReceiver);
}
