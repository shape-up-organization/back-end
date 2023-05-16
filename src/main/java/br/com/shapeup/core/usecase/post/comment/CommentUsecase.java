package br.com.shapeup.core.usecase.post.comment;

import br.com.shapeup.adapters.input.web.controller.request.post.comment.CommentRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.comments.CommentResponse;
import br.com.shapeup.common.exceptions.comment.CommentIsNotYours;
import br.com.shapeup.common.exceptions.comment.CommentNotFoundException;
import br.com.shapeup.common.exceptions.post.PostNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.post.comment.CommentInput;
import br.com.shapeup.core.ports.output.post.PostOutput;
import br.com.shapeup.core.ports.output.post.commment.CommentOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import java.util.List;

public class CommentUsecase implements CommentInput {
    private final FindUserOutput findUserOutput;
    private final CommentOutput commentOutput;
    private final PostOutput postOutput;

    public CommentUsecase(FindUserOutput findUserOutput, CommentOutput commentOutput, PostOutput postOutput) {
        this.findUserOutput = findUserOutput;
        this.commentOutput = commentOutput;
        this.postOutput = postOutput;
    }

    @Override
    public void createComment(String email, CommentRequest commentRequest) {
        User user = findUserOutput.findByEmail(email);

        validateExistPost(commentRequest.getPostId());

        commentOutput.createComment(user, commentRequest);
    }

    @Override
    public void deleteComment(String email, String commentId) {
        User user = findUserOutput.findByEmail(email);

        validateExistComment(commentId);

        validateIsYourComment(user, commentId);

        commentOutput.deleteComment(commentId);
    }

    private void validateExistComment(String commentId) {
        boolean existComment = commentOutput.existComment(commentId);

        if(!existComment) {
            throw new CommentNotFoundException(commentId);
        }
    }

    private void validateIsYourComment(User user, String commentId) {
        boolean isYourComment =
                commentOutput.isYourComment(user.getId().getValue(), commentId);

        if(!isYourComment) {
            throw new CommentIsNotYours(commentId);
        }
    }

    @Override
    public List<CommentResponse> getPostComments(String postId, int page, int size) {
        validateExistPost(postId);

        return commentOutput.getPostComments(postId, page, size);
    }

    private void validateExistPost(String postId) {
        boolean existPost = postOutput.existsPostById(postId);

        if(!existPost) {
            throw new PostNotFoundException(postId);
        }
    }

    @Override
    public CommentResponse getComment(String commentId) {
        validateExistComment(commentId);

        return commentOutput.getCommentById(commentId);
    }
}
