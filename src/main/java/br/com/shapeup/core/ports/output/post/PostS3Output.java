package br.com.shapeup.core.ports.output.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.common.utils.QueueObj;
import br.com.shapeup.core.domain.user.User;
import java.net.URL;
import java.util.List;

public interface PostS3Output {
    List<URL> createPost(Object[] files, User user, PostRequest request);

    void deletePostPhotos(User user, String postId);

    void createPostAsync(QueueObj<Object> files, User user, PostRequest request);

    void deletePostPhotosByUserId(String value);
}
