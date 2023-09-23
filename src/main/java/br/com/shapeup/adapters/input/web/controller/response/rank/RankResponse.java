package br.com.shapeup.adapters.input.web.controller.response.rank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RankResponse {
    private String firstName;

    private String lastName;

    private String username;

    private String profilePicture;

    private Long xp;
}
