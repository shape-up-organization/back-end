package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.post.PostInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@CrossOrigin(origins = "*")
public class PostController {
    private final PostInput postPersistenceInput;

    @PostMapping
    public ResponseEntity<List<Map<String, URL>>> createPost(PostRequest request,
                                           @RequestParam("file") MultipartFile[] files,
                                           HttpServletRequest jwtToken) {
        String token = TokenUtils.getToken(jwtToken);

        List<URL> postUrls =
                postPersistenceInput.createPost(files, token, request);

        List<Map<String, URL>> postUrlResponse = postUrls.stream()
                .map(postUrl -> Collections.singletonMap("urlPost", postUrl))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(postUrlResponse);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(HttpServletRequest jwtToken,
                                                                 @PathVariable String username,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        List<PostResponse> posts = postPersistenceInput.getPostsByUsername(email, username, page, size);

        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostsById(HttpServletRequest jwtToken,
                                                     @PathVariable String postId
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        PostResponse post = postPersistenceInput.getPostsById(email, postId);

        return ResponseEntity.status(HttpStatus.OK).body(post);
    }

    @GetMapping()
    public ResponseEntity<List<PostResponse>> getUserPosts(HttpServletRequest jwtToken,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        List<PostResponse> posts = postPersistenceInput.getUserPosts(email, page, size);

        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable String postId,
                                         HttpServletRequest jwtToken) {
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        postPersistenceInput.likePost(postId, email);

        return ResponseEntity.noContent().build();
    }
}
