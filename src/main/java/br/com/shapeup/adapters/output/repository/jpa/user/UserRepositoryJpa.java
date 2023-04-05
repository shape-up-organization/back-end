package br.com.shapeup.adapters.output.repository.jpa.user;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity, UUID> {
    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByCellPhone(String cellPhone);

    Optional<UserEntity> findByEmail(String email);

    void deleteByEmail(String email);

    Optional<UserEntity> findById(UUID uuid);

    Boolean existsByCellPhoneContains(@NonNull String cellPhone);

    Optional<UserEntity> findByUsername(String username);

}
