package br.com.shapeup.core.ports.output.user.profile;

import java.net.URL;

public interface ProfilePictureOutput {
    URL uploadPicture(Object file, String token);

}
