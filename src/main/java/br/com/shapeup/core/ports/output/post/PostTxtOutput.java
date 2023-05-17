package br.com.shapeup.core.ports.output.post;

import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.core.domain.user.User;

public interface PostTxtOutput {
    Object generatePostTxt(PostResponse postResponse);

    void readTxtAndPush(Object file, User user);
}
