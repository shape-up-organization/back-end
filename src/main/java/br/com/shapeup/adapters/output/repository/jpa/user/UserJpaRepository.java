package br.com.shapeup.adapters.output.repository.jpa.user;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID uuid);

    Boolean existsByCellPhoneContains(@NonNull String cellPhone);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByFullNameContainsIgnoreCase(String fullName);

    List<UserEntity> findAllByUsernameStartingWithIgnoreCase(String username);

    @Query("SELECT new UserEntity (u.name, u.lastName, u.username, u.profilePicture, u.xp) " +
            "FROM UserEntity u WHERE u.id IN " +
            "(SELECT f.userReceiver.id FROM FriendsEntity f WHERE f.userSender.id = :userReceiver) " +
            "OR u.id = :userReceiver " +
            "ORDER BY u.xp DESC")
    Page<UserEntity> getUserRank(@Param("userReceiver") UUID userReceiver, Pageable pageable);

    Page<UserEntity> findAllByOrderByXpDesc(Pageable pageable);

    List<UserEntity> findTop10ByOrderByBirth();

    @Query("SELECT u.xp FROM UserEntity u WHERE u.username = :username")
    Long findXpByUsername(String username);
}
