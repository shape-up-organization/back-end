package br.com.shapeup.core.usecase.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.core.ports.input.post.PostInput;
import br.com.shapeup.core.ports.output.post.PostOutput;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;
import java.util.List;

public class  PostUsecase implements PostInput {
    private final PostOutput postOutput;

    public PostUsecase(PostOutput postOutput) {
        this.postOutput = postOutput;
    }

    @Override
    public List<URL> createPost(MultipartFile[] files, String token, PostRequest request) {
        return postOutput.createPost(files, token, request);
    }
}
