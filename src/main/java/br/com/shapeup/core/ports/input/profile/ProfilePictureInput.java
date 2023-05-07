package br.com.shapeup.core.ports.input.profile;

import java.net.URL;

public interface ProfilePictureInput {
    URL uploadPicture(Object file, String token);
    void deletePicture(String username);
}
