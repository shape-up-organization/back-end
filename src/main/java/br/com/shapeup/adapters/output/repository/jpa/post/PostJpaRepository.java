package br.com.shapeup.adapters.output.repository.jpa.post;

import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostJpaRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findPostEntitiesByUserEntityOrderByCreatedAtDesc(UserEntity userEntity, Pageable pageable);

    @Query("SELECT new PostEntity(p.id, p.description, p.userEntity, p.createdAt) " +
            "FROM PostEntity p WHERE p.userEntity.id IN " +
            "(SELECT f.userReceiver.id FROM FriendsEntity f WHERE f.userSender.id = :userReceiver) " +
            "OR p.userEntity.id = :userReceiver " +"" +
            "ORDER BY p.createdAt DESC")
    Page<PostEntity> findPostFriends(@Param("userReceiver") UUID userReceiver, Pageable pageable);

    boolean existsByUserEntity(UserEntity userToUserEntity);

    boolean existsByUserEntityAndId(UserEntity userEntity, UUID postIdUUID);

    void deleteAllByUserEntityId(UUID userId);

    List<PostEntity> findAllByUserEntityId(UUID userId);
}
