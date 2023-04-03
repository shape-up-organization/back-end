package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.core.ports.input.UserPersistanceInput;
import br.com.shapeup.core.ports.output.UserPersistanceOutput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RequestMapping(value = "users")
public class UserController {
    private final UserPersistanceInput userPersistanceInput;

    private final UserPersistanceOutput userPersistanceOutput;

    @DeleteMapping()
    public ResponseEntity<Void> deleteByEmail(@RequestHeader(value = "Authorization")
                                              String token) {
        String jwtToken = token.replace("Bearer ", "");
        var email = JwtService.extractEmailFromToken(jwtToken);
        userPersistanceInput.deleteByEmail(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PostMapping("/picture")
    public ResponseEntity<Map<String, URL>> uploadPicture(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);

        var uploadPictureProfile = userPersistanceInput.uploadPicture(file, token);
        var urlUserPictureProfileResponse = Map.of("uploadPictureProfile-picture-profile", uploadPictureProfile);

        return ResponseEntity.status(HttpStatus.OK.value()).body(urlUserPictureProfileResponse);
    }

    @PutMapping()
    public ResponseEntity<Void> updateUserField(@RequestHeader(value = "Authorization") String token,
                                                @RequestBody UserRequest userRequest) {
        String jwtToken = token.replace("Bearer ", "");
        var email = JwtService.extractEmailFromToken(jwtToken);

        userPersistanceInput.updateUser(email, userRequest);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }
}
