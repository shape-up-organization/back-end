package br.com.shapeup.adapters.output.repository.jpa;

import br.com.shapeup.adapters.output.repository.model.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UserRepositoryJpa extends JpaRepository<UserEntity, UUID> {
    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    void deleteByEmail(String email);
}
