package br.com.shapeup.core.usecase.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.common.exceptions.post.PostNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.post.PostInput;
import br.com.shapeup.core.ports.output.post.PostOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import java.net.URL;
import java.util.List;

public class  PostUsecase implements PostInput {
    private final PostOutput postOutput;
    private final FindUserOutput findUserOutput;

    public PostUsecase(PostOutput postOutput, FindUserOutput findUserOutput) {
        this.postOutput = postOutput;
        this.findUserOutput = findUserOutput;
    }

    @Override
    public List<URL> createPost(Object[] files, String email, PostRequest request) {
        User user = findUserOutput.findByEmail(email);

        return postOutput.createPost(files, user, request);
    }

    @Override
        public List<PostResponse> getPostsByUsername(String username, int page, int size) {
        User user = findUserOutput.findByUsername(username);

        if (!hasPosts(user, page, size)) {
            return null;
        };

        return postOutput.getPostsByUsername(user, page, size);
    }

    private boolean hasPosts(User user, int page, int size) {
        return postOutput.existsPostByUsername(user, page, size);
    }

    @Override
    public PostResponse getPostsById(String id) {
        validateExistsPost(id);

        return postOutput.getPostById(id);
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
