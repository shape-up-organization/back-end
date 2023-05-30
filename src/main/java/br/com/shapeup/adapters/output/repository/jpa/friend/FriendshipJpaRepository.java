package br.com.shapeup.adapters.output.repository.jpa.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendsEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipJpaRepository extends JpaRepository<FriendsEntity, UUID> {

    List<FriendsEntity> findAllByUserReceiver(UserEntity userReceiver);

    void deleteByUserReceiverIdAndUserSenderId(UUID userReceiverId, UUID userSenderId);

    Boolean existsByUserSenderAndUserReceiverAndAcceptedIsTrue(UserEntity currentUserEntity, UserEntity newFriendEntity);

    void deleteAllByUserReceiverIdOrUserSenderId(UUID userReceiverId, UUID userSenderId);
}
