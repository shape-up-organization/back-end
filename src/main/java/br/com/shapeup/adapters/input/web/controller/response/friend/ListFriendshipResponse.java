package br.com.shapeup.adapters.input.web.controller.response.friend;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListFriendshipResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String username;
    private Long xp;
    private String profilePicture;
}
