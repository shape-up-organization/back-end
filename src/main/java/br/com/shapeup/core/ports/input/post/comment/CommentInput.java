package br.com.shapeup.core.ports.input.post.comment;

import br.com.shapeup.adapters.input.web.controller.request.post.comment.CommentRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.comments.CommentResponse;
import java.util.List;

public interface CommentInput {
    void createComment(String email, CommentRequest commentRequest);

    void deleteComment(String email, String commentId);

    List<CommentResponse> getPostComments(String postId, int page, int size);

    CommentResponse getComment(String commentId);
}
