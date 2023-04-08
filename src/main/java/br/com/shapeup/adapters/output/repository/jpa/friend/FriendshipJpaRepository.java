package br.com.shapeup.adapters.output.repository.jpa.friend;

import br.com.shapeup.adapters.output.repository.model.friend.FriendsEntity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipJpaRepository extends JpaRepository<FriendsEntity, UUID> {
    @Query("SELECT f FROM FriendsEntity f WHERE f.userSender.id = :userId OR f.userReceiver.id = :userId AND f.accepted = true")
    List<FriendsEntity> findAllFriendsByUserId(@Param("userId") UUID userId);

    void deleteByUserReceiverIdAndUserSenderId(UUID userReceiverId, UUID userSenderId);

}
