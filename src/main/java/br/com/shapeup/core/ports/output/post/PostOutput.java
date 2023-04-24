package br.com.shapeup.core.ports.output.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.core.domain.post.Post;
import br.com.shapeup.core.domain.user.User;
import org.springframework.data.domain.Page;

import java.net.URL;
import java.util.List;

public interface PostOutput {
    List<URL> createPost(Object[] files, String token, PostRequest request);

    List<PostResponse> getPostsByUsername(String username, int page, int size);

    PostResponse getPostById(String id);

    List<PostResponse> getUserPosts(String email ,int page, int size);
}
