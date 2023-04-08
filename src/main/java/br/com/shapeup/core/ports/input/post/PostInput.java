package br.com.shapeup.core.ports.input.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;
import java.util.List;

public interface PostInput {
    List<URL> createPost(MultipartFile[] files, String token, PostRequest request);
}
