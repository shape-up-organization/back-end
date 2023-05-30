package br.com.shapeup.adapters.output.integration.post.like;

import br.com.shapeup.adapters.output.repository.model.post.post.PostLikeEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostLikeMongoRepository;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.post.like.PostLikeOutput;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class PostLikeAdapter implements PostLikeOutput {
    private final PostLikeMongoRepository postLikeMongoRepository;

    @Transactional
    @Override
    public void likePost(User user, String postId) {
       postLikeMongoRepository.save(
                    new PostLikeEntity(
                            UUID.randomUUID(),
                            user.getId().getValue(),
                            postId
                    ));
    }

    @Override
    public boolean postIsAlreadyLiked(User user, String postId) {
        return postLikeMongoRepository.existsByPostIdAndUserId(
                postId,
                user.getId().getValue()
        );
    }

    @Override
    public void unlikePost(User user, String postId) {
        postLikeMongoRepository.deleteByUserIdAndPostId(user.getId().getValue(), postId);
    }
}
