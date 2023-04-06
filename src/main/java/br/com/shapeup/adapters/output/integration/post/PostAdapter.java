package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.core.ports.output.post.PostOutput;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@AllArgsConstructor
public class PostAdapter implements PostOutput {
    @Override
    public void createPost(MultipartFile[] files, String token, PostRequest request) {
        log.info("creating post");


    }
}
