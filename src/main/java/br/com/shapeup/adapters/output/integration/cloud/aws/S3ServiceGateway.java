package br.com.shapeup.adapters.output.integration.cloud.aws;

import br.com.shapeup.adapters.output.repository.model.profile.PictureProfile;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public interface S3ServiceGateway {
    URI uploadFile(PictureProfile multipartFile) throws URISyntaxException;
    URI uploadFile(InputStream inputStream, String fileName, String contentType, String uuid);
    URL getPictureUrl(PictureProfile pictureProfile);
}
