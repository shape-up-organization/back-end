package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.profile.ProfilePictureInput;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfilePictureInput profilePictureInput;

    @PostMapping("/picture")
    public ResponseEntity<Map<String, URL>> uploadPicture(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        String token = TokenUtils.getToken(request);


        var uploadPictureProfile = profilePictureInput.uploadPicture(file, token);
        var urlUserPictureProfileResponse = Map.of("picture-profile", uploadPictureProfile);

        return ResponseEntity.status(HttpStatus.OK.value()).body(urlUserPictureProfileResponse);
    }
}
