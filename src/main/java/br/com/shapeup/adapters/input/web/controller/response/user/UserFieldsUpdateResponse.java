package br.com.shapeup.adapters.input.web.controller.response.user;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserFieldsUpdateResponse(String token, String updatedAt) {
}
