package br.com.shapeup.adapters.input.web.controller.request.verification;

public record ValidationEmailRequest(
        String email,
        String code
) {
}
