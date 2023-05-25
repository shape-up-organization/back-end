package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.response.profile.UpdatedProfilePictureReponse;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.profile.ProfilePictureInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/shapeup/profiles")
@CrossOrigin(origins = "*")
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

    @DeleteMapping("/picture")
    public ResponseEntity<?> deletePicture(HttpServletRequest request) throws MalformedURLException {
        String token = TokenUtils.getToken(request);
        String username = JwtService.extractAccountNameFromToken(token);

        profilePictureInput.deletePicture(username);
        URL defaultProfilePicture = new URL("https://shapeup-user-profile.s3.amazonaws.com/profile_picture/default-user.png");

        String newToken = TokenUtils.updateProfilePictureAndGenerateNewToken(token, defaultProfilePicture);

        var updatedProfilePictureReponse = new UpdatedProfilePictureReponse(
                newToken,
                defaultProfilePicture.toString()
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(updatedProfilePictureReponse);
    }
}
