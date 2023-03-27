package br.com.shapeup.core.ports.input.user.profile;

import java.net.URL;

public interface ProfilePictureInput {
    URL uploadPicture(Object file, String token);
}
