package br.com.shapeup.adapters.output.repository.jpa.post;

import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostJpaRepository extends JpaRepository<PostEntity, UUID> {
}
