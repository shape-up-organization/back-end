package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.response.profile.UpdatedProfilePictureReponse;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.profile.ProfilePictureInput;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfilePictureInput profilePictureInput;

    @PostMapping("/picture")
    public ResponseEntity<UpdatedProfilePictureReponse> uploadPicture(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        String token = TokenUtils.getToken(request);

        var uploadProfilePicture = profilePictureInput.uploadPicture(file, token);
        String newToken = TokenUtils.updateProfilePictureAndGenerateNewToken(token, uploadProfilePicture);

        var updatedProfilePictureReponse = new UpdatedProfilePictureReponse(
                newToken,
                uploadProfilePicture.toString()
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(updatedProfilePictureReponse);
    }
}
