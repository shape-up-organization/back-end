package br.com.shapeup.adapters.output.integration.post.like;

import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.post.post.PostLikeEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostLikeMongoRepository;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.post.like.PostLikeOutput;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class PostLikeAdapter implements PostLikeOutput {
    private final PostLikeMongoRepository postLikeMongoRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public void likePost(User user, String postId) {
        UserEntity userEntity = userMapper.userToUserEntity(user);

        String userId = userEntity.getId().toString();

        boolean existsUser =
                postLikeMongoRepository.existsByUserId(userId);

        if(existsUser) {
            postLikeMongoRepository.deleteByUserId(userId);
        }

        if(!existsUser) {
            postLikeMongoRepository.save(
                    new PostLikeEntity(UUID.randomUUID(), userId, postId));
        }
    }
}
