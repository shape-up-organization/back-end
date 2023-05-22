package br.com.shapeup.core.ports.output.post.like;

import br.com.shapeup.core.domain.user.User;
public interface PostLikeOutput {
    void likePost(User user, String postId);

    void unlikePost(User user, String postId);

    boolean postIsAlreadyLiked(User user, String postId);
}
