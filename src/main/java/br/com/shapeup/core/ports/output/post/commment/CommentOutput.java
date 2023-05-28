package br.com.shapeup.core.ports.output.post.commment;

import br.com.shapeup.adapters.input.web.controller.request.post.comment.CommentRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.comments.CommentResponse;
import br.com.shapeup.core.domain.user.User;
import java.util.List;

public interface CommentOutput {
    boolean existComment(String commentId);

    void createComment(User user, CommentRequest commentRequest);

    void deleteComment(String commentId);

    boolean isYourComment(String id, String commentId);

    List<CommentResponse> getPostComments(String postId, int page, int size);

    CommentResponse getCommentById(String commentId);

    void deleteCommentsByPostId(String postId);

    void deleteAllCommentByUserId(String value);
}
