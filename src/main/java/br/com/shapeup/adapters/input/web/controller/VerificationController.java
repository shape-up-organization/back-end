package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.verification.ValidationEmailRequest;
import br.com.shapeup.adapters.input.web.controller.request.verification.ValidationResetPasswordRequest;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.verification.VerificationEmailInput;
import br.com.shapeup.core.ports.input.verification.VerificationResetPasswordInput;
import br.com.shapeup.security.service.JwtService;
import io.vavr.control.Try;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/shapeup/verification")
public class VerificationController {

    private final VerificationEmailInput verificationEmailInput;
    private final VerificationResetPasswordInput verificationResetPasswordInput;

    @PostMapping("/send-email-code/{email}")
    public ResponseEntity<?> sendVerificationEmailCode(
            @PathVariable(value = "email", required = false) String emailFromRequest,
            HttpServletRequest request
    ) {
        String email = resolveEmail(emailFromRequest, request);

        Try.run(() -> verificationEmailInput.createVerificationCode(email))
                .onFailure(Throwable::printStackTrace);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/confirm-email")
    public ResponseEntity<?> validateEmail(
            @RequestBody ValidationEmailRequest validationEmailRequest,
            HttpServletRequest request
    ) {
        String email = resolveEmail(validationEmailRequest.email(), request);

        Try.run(() -> verificationEmailInput.confirmEmail(email, validationEmailRequest.code()))
                .onFailure(Throwable::printStackTrace);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/email-is-verified")
    ResponseEntity<Boolean> isVerified(
            String emailFromRequest,
            HttpServletRequest request
    ) {
        String username = resolveEmail(emailFromRequest, request);

        return Try.of(() -> verificationEmailInput.isVerified(username))
                .map(ResponseEntity::ok)
                .onFailure(Throwable::printStackTrace)
                .getOrElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping("send-reset-password-code/{email}")
    public ResponseEntity<?> sendVerificationResetPasswordCode(
            @PathVariable(value = "email", required = false) String emailFromRequest,
            HttpServletRequest request
    ) {
        String email = resolveEmail(emailFromRequest, request);

        Try.run(() -> verificationResetPasswordInput.createVerificationCode(email))
                .onFailure(Throwable::printStackTrace);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/confirm-reset-password")
    public ResponseEntity<?> validateResetPassword(
            @RequestBody ValidationResetPasswordRequest validationResetPasswordRequest,
            HttpServletRequest request
    ) {
        Try.run(() -> verificationResetPasswordInput.confirmResetPassword(
                validationResetPasswordRequest.email(),
                validationResetPasswordRequest.code()
        )).onFailure(Throwable::printStackTrace);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private String resolveEmail(String emailFromRequest, HttpServletRequest request) {
        if (emailFromRequest == null) {
            String token = TokenUtils.getToken(request);
            return JwtService.extractEmailFromToken(token);
        }

        return emailFromRequest;
    }

    @GetMapping("/reset-password-is-verified")
    ResponseEntity<Boolean> isResetPasswordVerified(
            String emailFromRequest,
            HttpServletRequest request
    ) {
        String email = resolveEmail(emailFromRequest, request);

        return Try.of(() -> verificationResetPasswordInput.isVerified(email))
                .map(ResponseEntity::ok)
                .onFailure(Throwable::printStackTrace)
                .getOrElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
