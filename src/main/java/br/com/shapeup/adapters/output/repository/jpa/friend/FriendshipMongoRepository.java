package br.com.shapeup.adapters.output.repository.jpa.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendshipRequestEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipMongoRepository extends MongoRepository<FriendshipRequestEntity, String> {
    Boolean existsByUsernameSenderAndUsernameReceiver(String usernameSender, String usernameReceiver);
    FriendshipRequestEntity findByUsernameSenderAndUsernameReceiver(String usernameSender, String usernameReceiver);
    void deleteByUsernameSenderAndUsernameReceiver(String usernameSender,String usernameReceiver);
}
