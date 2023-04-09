package br.com.shapeup.core.ports.output.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import java.net.URL;
import java.util.List;

public interface PostOutput {
    List<URL> createPost(Object[] files, String token, PostRequest request);
}
