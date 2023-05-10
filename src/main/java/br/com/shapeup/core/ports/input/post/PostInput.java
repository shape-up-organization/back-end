package br.com.shapeup.core.ports.input.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.request.post.PostWithouPhotoRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import java.net.URL;
import java.util.List;

public interface PostInput {
    List<URL> createPost(Object[] files, String token, PostRequest request);

    List<PostResponse> getPostsByUsername(String email, String username, int page, int size);

    List<PostResponse> getPostsFriends(String email, int page, int size);

    PostResponse getPostsById (String email, String postId);

    void likePost(String postId, String email);

    void createPostWhitoutPhoto(String email, PostWithouPhotoRequest request);
}
