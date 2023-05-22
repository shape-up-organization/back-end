package br.com.shapeup.adapters.input.web.controller.response.user;

import lombok.Builder;

@Builder
public record UserFieldsUpdateResponse(String token, String updatedAt) {
}
