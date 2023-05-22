package br.com.shapeup.adapters.output.integration.cloud.aws.profile;

import br.com.shapeup.adapters.output.repository.model.profile.ProfilePicture;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public interface S3ServiceProfilePictureGateway {
    URI uploadProfileFilePicture(ProfilePicture multipartFile) throws URISyntaxException;
    URI uploadProfileFilePicture(InputStream inputStream, String fileName, String contentType, String uuid);
    URL getProfilePictureUrl(ProfilePicture profilePicture);
    Boolean haveProfilePictureInBucket(String username);
    void deletePicture(String username);
}
