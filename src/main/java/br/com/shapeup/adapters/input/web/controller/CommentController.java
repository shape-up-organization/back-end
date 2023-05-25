package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.post.comment.CommentRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.comments.CommentResponse;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.post.comment.CommentInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/shapeup/comments")
public class CommentController {
    private final CommentInput commentInput;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getPostComments(@PathVariable String postId,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size
    ){
        List<CommentResponse> comments = commentInput.getPostComments(postId, page, size);

        if (comments == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(200).body(comments);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable String commentId
    ){
        CommentResponse comment = commentInput.getComment(commentId);

        return ResponseEntity.status(200).body(comment);
    }

    @PostMapping
    public ResponseEntity<Void> createComment(HttpServletRequest jwtToken,
                                              @RequestBody @Valid CommentRequest commentRequest
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        commentInput.createComment(email, commentRequest);

        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(HttpServletRequest jwtToken,
                                              @PathVariable("commentId") String commentId
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        commentInput.deleteComment(email, commentId);

        return ResponseEntity.status(200).build();
    }
}
