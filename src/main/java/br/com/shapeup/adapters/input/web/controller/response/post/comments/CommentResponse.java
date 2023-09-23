package br.com.shapeup.adapters.input.web.controller.response.post.comments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {
    private String commentId;

    private String commentMessage;

    private String createdAt;

    private String username;

    private String profilePicture;

    private String firstName;

    private String lastName;

    private Long xp;
}
