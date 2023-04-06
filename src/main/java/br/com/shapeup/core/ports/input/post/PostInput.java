package br.com.shapeup.core.ports.input.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PostInput {
    void createPost(MultipartFile[] files, String token, PostRequest request);
}
