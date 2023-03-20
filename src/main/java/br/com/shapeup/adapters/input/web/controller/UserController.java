package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.user.UserHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.user.UserBiographyRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserBirthRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserCellphoneRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserLastNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserPasswordRequest;
import br.com.shapeup.core.ports.input.UserPersistanceInput;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.URL;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(value = "users")
public class UserController {
    private final UserPersistanceInput userPersistanceInput;

    private final UserHttpMapper userHttpMapper;

    @PutMapping("/name")
    public ResponseEntity<Void> updateName(@Valid @RequestBody UserNameRequest userNameRequest) {
        var user = userHttpMapper.toUser(userNameRequest);
        userPersistanceInput.updateName(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/last-name")
    public ResponseEntity<Void> updateLastName(@Valid @RequestBody UserLastNameRequest userLastNameRequest) {
        var user = userHttpMapper.toUser(userLastNameRequest);
        userPersistanceInput.updateLastName(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/cell-phone")
    public ResponseEntity<Void> updateCellPhone(@Valid @RequestBody UserCellphoneRequest userCellphoneRequest) {
        var user = userHttpMapper.toUser(userCellphoneRequest);
        userPersistanceInput.updateCellPhone(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/biography")
    public ResponseEntity<Void> updateBiography(@Valid @RequestBody UserBiographyRequest userBiographyRequest) {
        var user = userHttpMapper.toUser(userBiographyRequest);
        userPersistanceInput.updateBiography(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/birth")
    public ResponseEntity<Void> updateBirth(@Valid @RequestBody UserBirthRequest userBirthRequest) {
        var user = userHttpMapper.toUser(userBirthRequest);
        userPersistanceInput.updateBirth(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserPasswordRequest userPasswordRequest) {
        var user = userHttpMapper.toUser(userPasswordRequest);
        userPersistanceInput.updatePassword(user);

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteByEmail(@PathVariable String email) {

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
}
