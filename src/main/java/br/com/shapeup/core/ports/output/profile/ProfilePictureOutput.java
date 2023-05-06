package br.com.shapeup.core.ports.output.profile;

import java.net.URL;

public interface ProfilePictureOutput {
    URL uploadPicture(Object file, String token);

    Boolean haveProfilePictureInBucket(String username);

    void deletePicture(String username);
}
