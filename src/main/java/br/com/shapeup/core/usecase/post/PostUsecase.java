package br.com.shapeup.core.usecase.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.core.ports.input.post.PostInput;
import br.com.shapeup.core.ports.output.post.PostOutput;
import java.net.URL;
import java.util.List;

public class  PostUsecase implements PostInput {
    private final PostOutput postOutput;

    public PostUsecase(PostOutput postOutput) {
        this.postOutput = postOutput;
    }

    @Override
    public List<URL> createPost(Object[] files, String token, PostRequest request) {
        return postOutput.createPost(files, token, request);
    }

    @Override
    public List<PostResponse> getPostsByUsername(String username, int page, int size) {
        return postOutput.getPostsByUsername(username, page, size);
    }

    @Override
    public List<PostResponse> getUserPosts(String email, int page, int size) {
        return postOutput.getUserPosts(email, page, size);
    }

    @Override
    public PostResponse getPostsById(String id) {
        return postOutput.getPostById(id);
    }
}
