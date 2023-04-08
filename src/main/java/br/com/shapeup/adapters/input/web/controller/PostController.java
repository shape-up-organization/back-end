package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.core.ports.input.post.PostInput;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostInput postPersistenceInput;

    @PostMapping
    public ResponseEntity<List<Map<String, URL>>> createPost(PostRequest request,
                                           @RequestParam("file") MultipartFile[] files,
                                           HttpServletRequest jwtToken) {
        String token =
                jwtToken.getHeader("Authorization").substring(7);

        List<URL> postUrls =
                postPersistenceInput.createPost(files, token, request);

        List<Map<String, URL>> postUrlResponse = postUrls.stream()
                .map(postUrl -> Collections.singletonMap("urlPost", postUrl))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(postUrlResponse);
    }
}
