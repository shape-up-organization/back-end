package br.com.shapeup.core.usecase.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.core.domain.user.User;
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
    public List<URL> createPost(Object[] files, String token, PostRequest request) {
        return postOutput.createPost(files, token, request);
    }

    @Override
    public List<PostResponse> getPostsByUsername(String email, String username, int page, int size) {
        User user = findUserOutput.findByEmail(email);

        User otherUser = findUserOutput.findByUsername(username);

        return postOutput.getPostsByUsername(user, otherUser, page, size);
    }

    @Override
    public List<PostResponse> getUserPosts(String email, int page, int size) {
        User user = findUserOutput.findByEmail(email);

        return postOutput.getUserPosts(user, page, size);
    }

    @Override
    public PostResponse getPostsById(String email, String postId) {
        User user = findUserOutput.findByEmail(email);

        return postOutput.getPostById(user, postId);
    }

    @Override
    public void likePost(String postId, String email) {
        User user = findUserOutput.findByEmail(email);

        postLikeOutput.likePost(user, postId);
    }
}
