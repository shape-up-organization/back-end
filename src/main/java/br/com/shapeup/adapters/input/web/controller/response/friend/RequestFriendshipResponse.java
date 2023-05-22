package br.com.shapeup.adapters.input.web.controller.response.friend;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RequestFriendshipResponse {

    @NotNull
    private String id;
    @NotBlank
    private String usernameSender;
    @NotBlank
    private String usernameReceiver;
    @NotNull
    private Boolean accepted;
}
