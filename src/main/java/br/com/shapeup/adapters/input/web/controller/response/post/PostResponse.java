package br.com.shapeup.adapters.input.web.controller.response.post;

import java.util.List;
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
public class PostResponse {
    private String id;

    private String description;

    private String createdAt;

    private Integer countLike;

    private Integer countComments;

    private List<String> photoUrls;

    private boolean isLiked;

    private String username;

    private String profilePicture;

    private String firstName;

    private String lastName;

    private Long xp;
}
