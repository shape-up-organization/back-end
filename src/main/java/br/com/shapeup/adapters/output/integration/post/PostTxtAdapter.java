package br.com.shapeup.adapters.output.integration.post;

import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.adapters.output.repository.jpa.post.PostJpaRepository;
import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
import br.com.shapeup.adapters.output.repository.model.post.post.PostEntity;
import br.com.shapeup.adapters.output.repository.model.post.post.PostPhotoEntity;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.adapters.output.repository.mongo.post.PostPhotoMongoRepository;
import br.com.shapeup.common.exceptions.post.ErrorReadingTxtException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.post.PostTxtOutput;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostTxtAdapter implements PostTxtOutput {
    private final UserMapper userMapper;
    private final PostJpaRepository postJpaRepository;
    private final PostPhotoMongoRepository postPhotoMongoRepository;

    @Override
    public Object generatePostTxt(PostResponse postResponse) {

        String header =
                "00POST" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))+
                "01\n";

        String replaceDescription = postResponse.getDescription().replace("\n", "%$$#%");

        String body = "02" + String.format("%-1000.1000s\n", replaceDescription);

        for (String url : postResponse.getPhotoUrls()) {
            body += "03" + String.format("%-200.200s\n", url);
        }

        body += postResponse.getUsername() + "\n";
        body += postResponse.getFirstName() + "\n";
        body += postResponse.getLastName() + "\n";
        body += postResponse.getXp() + "\n";
        body += postResponse.getCountLike() + "\n";
        body += postResponse.getCountLike() + "\n";
        body += postResponse.getCreatedAt() + "\n";
        body += postResponse.getProfilePicture() + "\n";

        String trailer = "01SHAPEUP";
        String postTxt = header + body + trailer;

        return postTxt.getBytes();
    }

    @Override
    public void readTxtAndPush(Object file, User user) {
        List<String> postUrls = new ArrayList<>();
        String description = "";

        MultipartFile fileMult = (MultipartFile)file;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileMult.getInputStream()));
            String line = "";

            while ((line = reader.readLine()) != null)
            {
                if(line.startsWith("03")) {
                    String[] lineSplit = line.split("03");
                    String url = lineSplit[1].trim();

                    postUrls.add(url);
                }

                if(line.startsWith("02")) {
                    String[] lineSplit = line.split("02");
                    description = lineSplit[1].trim();
                    description = description.replace("%$$#%", "\n");
                }
            }
        }
        catch (Exception e) {
            log.error("Error reading txt file: " + e.getMessage());

            throw new ErrorReadingTxtException();
        }

        UUID postId = UUID.randomUUID();

        UserEntity userEntity = userMapper.userToUserEntity(user);

        PostEntity postEntity = new PostEntity(postId, userEntity, description);

        postJpaRepository.save(postEntity);

        for (String url: postUrls) {
            PostPhotoEntity postPhotoEntity = new PostPhotoEntity(url, postId.toString());

            postPhotoMongoRepository.save(postPhotoEntity);
        }
    }
}
