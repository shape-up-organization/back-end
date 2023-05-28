package br.com.shapeup.core.ports.output.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostWithouPhotoRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.core.domain.user.User;
import java.util.List;

public interface PostOutput {

    List<PostResponse> getPostsByUsername(User currentUser, User otherUser, int page, int size);

    PostResponse getPostById(User user, String id);

    List<PostResponse> getPostsFriends(User user, int page, int size);

    boolean existsPostById(String id);

    boolean existsPostByUsername(User user, int page, int size);

    boolean existsPostByIdAndUser(User user, String postId);

    void deletePostById(User user, String postId);

    void createPostWithoutPhoto(User user, PostWithouPhotoRequest request);

    void deleteAllPostsByUserId(String userId);
}
