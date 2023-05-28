package br.com.shapeup.adapters.output.integration.post.comment;

import br.com.shapeup.adapters.input.web.controller.request.post.comment.CommentRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.comments.CommentResponse;
import br.com.shapeup.adapters.output.repository.jpa.post.comment.PostCommentJpaRepository;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.post.comments.CommentEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.exceptions.comment.CommentNotFoundException;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.post.commment.CommentOutput;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CommentAdapter implements CommentOutput {
    private final PostCommentJpaRepository commentRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    public boolean existComment(String commentId) {
        return commentRepository.existsById(UUID.fromString(commentId));
    }

    @Override
    public void createComment(User user, CommentRequest commentRequest) {
        CommentEntity comment = new CommentEntity(
                commentRequest.getCommentMessage(),
                user.getId().getValue(),
                commentRequest.getPostId()
        );

        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        commentRepository.deleteById(UUID.fromString(commentId));
    }

    @Override
    public boolean isYourComment(String userId, String commentId) {
        UUID commentUUID = UUID.fromString(commentId);

        return commentRepository.existsByUserIdAndId(userId, commentUUID);
    }

    @Override
    public List<CommentResponse> getPostComments(String postId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<CommentEntity> comments = commentRepository.findAllByIdPostOrderByCreatedAt(postId, pageRequest);

        if (comments.hasContent()) {
            return comments
                    .map(comment -> {
                UUID userUUID = UUID.fromString(comment.getUserId());

                UserEntity user = userJpaRepository.findById(userUUID)
                        .orElseThrow(() -> new UserNotFoundException(comment.getUserId()));

                return getCommentResponse(comment, user);
            }).toList();
        }

        return null;
    }

    @Override
    public CommentResponse getCommentById(String commentId) {
        UUID commentUUID = UUID.fromString(commentId);

        CommentEntity comment = commentRepository.findById(commentUUID)
                .orElseThrow(() -> new CommentNotFoundException(commentId));

        UUID userUUID = UUID.fromString(comment.getUserId());

        UserEntity user = userJpaRepository.findById(userUUID)
                .orElseThrow(() -> new UserNotFoundException(comment.getUserId()));

        return getCommentResponse(comment, user);
    }

    private static CommentResponse getCommentResponse(CommentEntity comment, UserEntity user) {
        return new CommentResponse(
                comment.getId().toString(),
                comment.getCommentMessage(),
                comment.getCreatedAt().toString(),
                user.getUsername(),
                user.getProfilePicture(),
                user.getName(),
                user.getLastName(),
                user.getXp()
        );
    }

    @Override
    @Transactional
    public void deleteCommentsByPostId(String postId) {
        commentRepository.deleteAllByIdPost(postId);
    }

    @Override
    @Transactional
    public void deleteAllCommentByUserId(String userId) {
        commentRepository.deleteAllByUserId(userId);
    }
}
