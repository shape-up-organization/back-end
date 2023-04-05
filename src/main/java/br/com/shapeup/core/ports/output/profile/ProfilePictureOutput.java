package br.com.shapeup.core.ports.output.profile;

import java.net.URL;

public interface ProfilePictureOutput {
    URL uploadPicture(Object file, String token);

}
