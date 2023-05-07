package br.com.shapeup.adapters.output.repository.jpa.user;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findById(UUID uuid);

    Boolean existsByCellPhoneContains(@NonNull String cellPhone);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String name, String lastName);

    List<UserEntity> findAllByUsernameStartingWithIgnoreCase(String username);

}
