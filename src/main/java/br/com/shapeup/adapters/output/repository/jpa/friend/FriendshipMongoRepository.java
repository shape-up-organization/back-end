package br.com.shapeup.adapters.output.repository.jpa.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestDocument;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipMongoRepository extends MongoRepository<FriendshipRequestDocument, String> {

    void deleteById(String id);

    Optional<FriendshipRequestDocument> findByUsernameSenderAndUsernameReceiver(String usernameSender, String usernameReceiver);

    List<FriendshipRequestDocument> findAllByUsernameSenderAndUsernameReceiver(String usernameSender, String usernameReceiver);

    Optional<FriendshipRequestDocument> findByUsernameSenderAndUsernameReceiverAndAccepted(String usernameSender, String usernameReceiver, Boolean accepted);

    @Query("{'$or':[ {'usernameSender':?0}, {'usernameReceiver':?0} ]}")
    List<FriendshipRequestDocument> findAllByUsernameSenderOrUsernameReceiverEqualsIgnoreCase(String username);

    void deleteAllByUsernameSenderOrUsernameReceiverEqualsIgnoreCase(String usernameSender, String usernameReceiver);
}
