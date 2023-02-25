package br.com.shapeup.adapters.output.repository.jpa;

import br.com.shapeup.adapters.output.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserRepositoryJpa extends JpaRepository<UserEntity, Long> {
}
