package br.com.shapeup.core.usecase.post;

import br.com.shapeup.adapters.input.web.controller.request.post.PostRequest;
import br.com.shapeup.adapters.input.web.controller.request.post.PostWithouPhotoRequest;
import br.com.shapeup.adapters.input.web.controller.response.post.PostResponse;
import br.com.shapeup.common.exceptions.post.PostIsNotYoursException;
import br.com.shapeup.common.exceptions.post.PostNotFoundException;
import br.com.shapeup.common.utils.QueueObj;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.post.PostInput;
import br.com.shapeup.core.ports.output.post.PostOutput;
import br.com.shapeup.core.ports.output.post.PostS3Output;
import br.com.shapeup.core.ports.output.post.PostTxtOutput;
import br.com.shapeup.core.ports.output.post.commment.CommentOutput;
import br.com.shapeup.core.ports.output.post.like.PostLikeOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import java.net.URL;
import java.util.List;

public class PostUsecase implements PostInput {
    private final PostOutput postOutput;
    private final FindUserOutput findUserOutput;
    private final PostLikeOutput postLikeOutput;
    private final PostS3Output postS3Output;
    private final CommentOutput commentOutput;
    private final PostTxtOutput postTxtOutput;

    public PostUsecase(PostOutput postOutput, PostLikeOutput postLikeOutput,
                       FindUserOutput findUserOutput, PostS3Output postS3Output,
                       CommentOutput commentOutput, PostTxtOutput postTxtOutput) {
        this.postOutput = postOutput;
        this.postLikeOutput = postLikeOutput;
        this.findUserOutput = findUserOutput;
        this.postS3Output = postS3Output;
        this.commentOutput = commentOutput;
        this.postTxtOutput = postTxtOutput;
    }

    @Override
    public List<URL> createPost(Object[] files, String email, PostRequest request) {
        User user = findUserOutput.findByEmail(email);

        return postS3Output.createPost(files, user, request);
    }

    @Override
    public List<PostResponse> getPostsByUsername(String email, String username, int page, int size) {
        User currentUser = findUserOutput.findByEmail(email);
        User otherUser = findUserOutput.findByUsername(username);

        if (!hasPosts(otherUser, page, size)) {
            return null;
        };
        return postOutput.getPostsByUsername(currentUser, otherUser, page, size);
    }

    private boolean hasPosts(User user, int page, int size) {
        return postOutput.existsPostByUsername(user, page, size);
    }

    public PostResponse getPostsById(String email, String postId) {
        validateExistsPost(postId);

        User user = findUserOutput.findByEmail(email);

        return postOutput.getPostById(user, postId);
    }

    @Override
    public void likePost(String postId, String email) {
        validateExistsPost(postId);

        User user = findUserOutput.findByEmail(email);

        boolean isLiked = postLikeOutput.postIsAlreadyLiked(user, postId);

        if (isLiked) {
            postLikeOutput.unlikePost(user, postId);
        }

        if(!isLiked) {
            postLikeOutput.likePost(user, postId);
        }
    }

    private void validateExistsPost(String id) {
        if (!postOutput.existsPostById(id)) {
            throw new PostNotFoundException(id);
        }
    }

    private void validateIsYourPost(User user, String postId) {
        boolean isYourPost = postOutput.existsPostByIdAndUser(user, postId);

        if (!isYourPost) {
            throw new PostIsNotYoursException(postId);
        }
    }

    @Override
    public List<PostResponse> getPostsFriends(String email, int page, int size) {
        User user = findUserOutput.findByEmail(email);

        return postOutput.getPostsFriends(user, page, size);
    }

    @Override
    public void createPostWithoutPhoto(String email, PostWithouPhotoRequest request) {
        User user = findUserOutput.findByEmail(email);

        postOutput.createPostWithoutPhoto(user, request);
    }

    @Override
    public void deletePostById(String email, String postId) {
        User user = findUserOutput.findByEmail(email);

        validateExistsPost(postId);
        validateIsYourPost(user, postId);

        postS3Output.deletePostPhotos(user, postId);
        commentOutput.deleteCommentsByPostId(postId);
        postOutput.deletePostById(user, postId);
    }

    @Override
    public void createPostAsync(Object[] files, String email, PostRequest request) {
        User user = findUserOutput.findByEmail(email);

        QueueObj<Object> filesQueue = createQueueObj(files);

        postS3Output.createPostAsync(filesQueue, user, request);
    }

    private static QueueObj<Object> createQueueObj(Object[] files) {
        QueueObj<Object> filesQueue = new QueueObj<>(files.length);

        for (Object file : files) {
            filesQueue.insert(file);
        }
        return filesQueue;
    }

    @Override
    public Object generateTxt(String postId, String email) {
        User user = findUserOutput.findByEmail(email);

        PostResponse post = postOutput.getPostById(user, postId);

        return postTxtOutput.generatePostTxt(post);
    }

    @Override
    public void readTxt(Object file, String email) {
        User user = findUserOutput.findByEmail(email);

        postTxtOutput.readTxtAndPush(file, user);
    }
}
