package br.com.shapeup.core.usecase.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.common.exceptions.post.PostNotFoundException;
import br.com.shapeup.core.ports.input.post.PostInput;
import br.com.shapeup.core.ports.output.post.PostOutput;
import br.com.shapeup.core.ports.output.post.like.PostLikeOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import java.net.URL;
import java.util.List;

public class  PostUsecase implements PostInput {
    private final PostOutput postOutput;
    private final FindUserOutput findUserOutput;
    private final PostLikeOutput postLikeOutput;

    public PostUsecase(PostOutput postOutput, PostLikeOutput postLikeOutput, FindUserOutput findUserOutput) {
        this.postOutput = postOutput;
        this.postLikeOutput = postLikeOutput;
        this.findUserOutput = findUserOutput;
    }

    @Override
    public List<URL> createPost(Object[] files, String email, PostRequest request) {
        User user = findUserOutput.findByEmail(email);

        return postOutput.createPost(files, user, request);
    }

    @Override
    public List<PostResponse> getPostsByUsername(String email, String username, int page, int size) {
        User currentUser = findUserOutput.findByEmail(email);
        User otherUser = findUserOutput.findByUsername(username);

        if (!hasPosts(otherUser, page, size)) {
            return null;
        };
        return postOutput.getPostsByUsername(currentUser, otherUser, page, size);
    }

    private boolean hasPosts(User user, int page, int size) {
        return postOutput.existsPostByUsername(user, page, size);
    }

    public PostResponse getPostsById(String email, String postId) {
        validateExistsPost(postId);

        User user = findUserOutput.findByEmail(email);

        return postOutput.getPostById(user, postId);
    }

    @Override
    public void likePost(String postId, String email) {
        validateExistsPost(postId);

        User user = findUserOutput.findByEmail(email);

        postLikeOutput.likePost(user, postId);
    }

    private void validateExistsPost(String id) {
        if (!postOutput.existsPostById(id)) {
            throw new PostNotFoundException(id);
        }
    }

    @Override
    public List<PostResponse> getPostsFriends(String email, int page, int size) {
        User user = findUserOutput.findByEmail(email);

        return postOutput.getPostsFriends(user, page, size);
    }
}
