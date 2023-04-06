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

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostInput postPersistenceInput;

    @PostMapping
    public ResponseEntity<Void> createPost(PostRequest request,
                                           @RequestParam("file") MultipartFile[] files,
                                           HttpServletRequest jwtToken) {
        String token = jwtToken.getHeader("Authorization").substring(7);

        postPersistenceInput.createPost(files, token, request);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
