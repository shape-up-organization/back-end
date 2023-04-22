package br.com.shapeup.adapters.input.web.controller.response.user;

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
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String username;

    @NotBlank
    private String profilePicture;

    @NotNull
    private Long xp;

    @NotNull
    private Boolean isFriend;

    @NotNull
    private Boolean haveFriendRequest;
}
