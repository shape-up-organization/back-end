package br.com.shapeup.adapters.output.repository.jpa.post;

import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostJpaRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findPostEntitiesByUserEntityOrderByCreatedAtDesc(UserEntity userEntity, Pageable pageable);
}
