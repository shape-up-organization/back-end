package br.com.shapeup.adapters.output.repository.model.profile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProfilePicture implements MultipartFile {
    private MultipartFile file;
    private String uuid;

    public ProfilePicture(MultipartFile file, String uuid) {
        this.file = file;
        this.uuid = uuid;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return file.getOriginalFilename();
    }

    @Override
    public String getContentType() {
        return file.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return file.isEmpty();
    }

    @Override
    public long getSize() {
        return file.getSize();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return file.getBytes();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return file.getInputStream();
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        file.transferTo(dest);
    }
}
