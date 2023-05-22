package br.com.shapeup.core.usecase.profile;

import br.com.shapeup.common.exceptions.profile.ProfilePictureNotFoundException;
import br.com.shapeup.core.ports.input.profile.ProfilePictureInput;
import br.com.shapeup.core.ports.output.profile.ProfilePictureOutput;
import java.net.URL;

public class ProfilePictureUsecase implements ProfilePictureInput {
    private final ProfilePictureOutput profilePictureOutput;

    public ProfilePictureUsecase(ProfilePictureOutput profilePictureOutput) {
        this.profilePictureOutput = profilePictureOutput;
    }

    @Override
    public URL uploadPicture(Object file, String token) {
        return profilePictureOutput.uploadPicture(file, token);
    }

    @Override
    public void deletePicture(String username) {

        verifyInBucketIfUserHaveAnProfilePicture(username);

        profilePictureOutput.deletePicture(username);
    }

    private void verifyInBucketIfUserHaveAnProfilePicture(String username) {
        Boolean haveProfilePictureInBucket = profilePictureOutput.haveProfilePictureInBucket(username);

        if (!haveProfilePictureInBucket) {
            throw new ProfilePictureNotFoundException();
        }
    }
}
