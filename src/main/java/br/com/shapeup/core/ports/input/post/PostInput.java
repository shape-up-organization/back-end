package br.com.shapeup.core.ports.input.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import java.net.URL;
import java.util.List;

public interface PostInput {
    List<URL> createPost(Object[] files, String token, PostRequest request);
}
