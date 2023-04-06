package br.com.shapeup.core.usecase.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.core.ports.input.post.PostInput;
import br.com.shapeup.core.ports.output.post.PostOutput;
import org.springframework.web.multipart.MultipartFile;

public class  PostUsecase implements PostInput {
    private final PostOutput postOutput;

    public PostUsecase(PostOutput postOutput) {
        this.postOutput = postOutput;
    }

    @Override
    public void createPost(MultipartFile[] files, String token, PostRequest request) {
        postOutput.createPost(files, token, request);
    }
}
