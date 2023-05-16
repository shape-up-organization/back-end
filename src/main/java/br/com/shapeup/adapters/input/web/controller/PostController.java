package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.request.post.PostWithouPhotoRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.post.PostInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@CrossOrigin(origins = "*")
public class PostController {
    private final PostInput postPersistenceInput;

    @PostMapping
    public ResponseEntity<List<Map<String, URL>>> createPost(@Valid PostRequest request,
                                           @RequestParam("file") MultipartFile[] files,
                                           HttpServletRequest jwtToken
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        List<URL> postUrls =
                postPersistenceInput.createPost(files, email, request);

        List<Map<String, URL>> postUrlResponse = postUrls.stream()
                .map(postUrl -> Collections.singletonMap("urlPost", postUrl))
                .toList();

        return  ResponseEntity.status(200).body(postUrlResponse);
    }

    @PostMapping("/async")
    public ResponseEntity<Void> createPostAsync(@Valid PostRequest request,
                                                             @RequestParam("file") MultipartFile[] files,
                                                             HttpServletRequest jwtToken
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        postPersistenceInput.createPostAsync(files, email, request);

        return  ResponseEntity.noContent().build();
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

        if (posts == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(200).body(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostsById(HttpServletRequest jwtToken,
                                                     @PathVariable String postId
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        PostResponse post = postPersistenceInput.getPostsById(email, postId);

        return ResponseEntity.status(200).body(post);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getPostsFriends(HttpServletRequest jwtToken,
                                                                 @RequestParam("page") int page,
                                                                 @RequestParam("size") int size
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        List<PostResponse> posts = postPersistenceInput.getPostsFriends(email, page, size);

        if (posts.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }

        return  ResponseEntity.status(200).body(posts);
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<Void> likePost(@PathVariable String postId,
                                         HttpServletRequest jwtToken
    ) {
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        postPersistenceInput.likePost(postId, email);

        return ResponseEntity.status(204).build();
    }

    @PostMapping("/without-photo")
    public ResponseEntity<Void> createPostWhitoutPhoto(@RequestBody @Valid PostWithouPhotoRequest request,
                                                       HttpServletRequest jwtToken
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        postPersistenceInput.createPostWithoutPhoto(email, request);

        return  ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(HttpServletRequest jwtToken,
                                               @PathVariable String postId
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        postPersistenceInput.deletePostById(email, postId);

        return ResponseEntity.status(200).build();
    }
}
