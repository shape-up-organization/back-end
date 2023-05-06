package br.com.shapeup.common.exceptions.profile;

public class ProfilePictureNotFoundException extends RuntimeException {
    public ProfilePictureNotFoundException(String message) {
        super(message);
    }

    public ProfilePictureNotFoundException() {
        super("Profile picture not found in bucket");
    }
}
