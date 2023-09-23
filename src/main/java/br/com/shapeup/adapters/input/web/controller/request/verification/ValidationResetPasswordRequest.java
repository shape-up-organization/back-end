package br.com.shapeup.adapters.input.web.controller.request.verification;

public record ValidationResetPasswordRequest(
        String email,
        String code
) {
}
