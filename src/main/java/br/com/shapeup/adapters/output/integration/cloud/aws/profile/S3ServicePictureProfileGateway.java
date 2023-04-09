package br.com.shapeup.adapters.output.integration.cloud.aws.profile;

import br.com.shapeup.adapters.output.repository.model.profile.PictureProfile;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public interface S3ServicePictureProfileGateway {
    URI uploadPictureProfileFile(PictureProfile multipartFile) throws URISyntaxException;
    URI uploadPictureProfileFile(InputStream inputStream, String fileName, String contentType, String uuid);
    URL getPictureProfileUrl(PictureProfile pictureProfile);
}
