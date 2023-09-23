package br.com.shapeup.adapters.input.web.controller.response.user;

import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotBlank
    private String profilePicture;

    @NotBlank
    private String biography;

    @NotNull
    private Long xp;

    @NotNull
    private FriendshipStatus friendshipStatus;
}
