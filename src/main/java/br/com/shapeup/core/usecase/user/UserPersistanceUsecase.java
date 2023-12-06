package br.com.shapeup.core.usecase.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
import br.com.shapeup.adapters.output.repository.model.friend.FriendshipStatus;
import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.user.UserPersistanceInput;
import br.com.shapeup.core.ports.output.friend.FriendshipOutput;
import br.com.shapeup.core.ports.output.post.PostOutput;
import br.com.shapeup.core.ports.output.post.PostS3Output;
import br.com.shapeup.core.ports.output.post.commment.CommentOutput;
import br.com.shapeup.core.ports.output.quest.RemoveTrainingDayByTrainingIdAndUserIdOutputPort;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import br.com.shapeup.core.ports.output.user.UserPersistanceOutput;
import br.com.shapeup.core.ports.output.verification.VerificationEmailOutputPort;
import java.util.List;
import java.util.UUID;

public class UserPersistanceUsecase implements UserPersistanceInput {
    private final UserPersistanceOutput userPersistanceOutput;

    private final CommentOutput commentOutput;

    private final PostOutput postOutput;

    private final FriendshipOutput friendshipOutput;

    private final FindUserOutput findUserOutput;

    private final PostS3Output postS3Output;
    private final VerificationEmailOutputPort verificationEmailOutputPort;
    private final RemoveTrainingDayByTrainingIdAndUserIdOutputPort removeTrainingDayByTrainingIdAndUserIdOutputPort;

    public UserPersistanceUsecase(UserPersistanceOutput userPersistanceOutput,
                                  CommentOutput commentOutput,
                                  PostOutput postOutput,
                                  FriendshipOutput friendshipOutput,
                                  FindUserOutput findUserOutput,
                                  PostS3Output postS3Output,
                                  VerificationEmailOutputPort verificationEmailOutputPort,
                                  RemoveTrainingDayByTrainingIdAndUserIdOutputPort removeTrainingDayByTrainingIdAndUserIdOutputPort
    ) {
        this.userPersistanceOutput = userPersistanceOutput;
        this.commentOutput = commentOutput;
        this.postOutput = postOutput;
        this.friendshipOutput = friendshipOutput;
        this.findUserOutput = findUserOutput;
        this.postS3Output = postS3Output;
        this.verificationEmailOutputPort = verificationEmailOutputPort;
        this.removeTrainingDayByTrainingIdAndUserIdOutputPort = removeTrainingDayByTrainingIdAndUserIdOutputPort;
    }

    @Override
    public void deleteByEmail(String email) {
        User user = findUserOutput.findByEmail(email);

        commentOutput.deleteAllCommentByUserId(user.getId().getValue());
        postOutput.deleteAllPostsByUserId(user.getId().getValue());
        friendshipOutput.deleteAllFriendshipByUserId(user);
        removeTrainingDayByTrainingIdAndUserIdOutputPort.deleteByUserId(UUID.fromString(user.getId().getValue()));
        postS3Output.deletePostPhotosByUserId(user.getId().getValue());
//        verificationEmailOutputPort.deleteByEmail(user.getEmail().getValue());

        userPersistanceOutput.deleteById(user);
    }

    @Override
    public void updateUser(String email, UserRequest userRequest) {
        userPersistanceOutput.updateUser(email, userRequest);
    }

    @Override
    public User findUserByUsername(String username) {
        User searchUser = userPersistanceOutput.findUserByUsername(username);
        validateSearchUser(searchUser, username);
        return searchUser;
    }

    @Override
    public List<FriendshipStatus> getFriendshipStatus(String currentUserEmail, List<String> searchUserUsername) {
        return userPersistanceOutput.getFriendshipStatusForUser(currentUserEmail, searchUserUsername);
    }

    @Override
    public List<User> findAllUserByFullName(String fullName) {
        return userPersistanceOutput.findAllUserByFullName(fullName);
    }

    @Override
    public List<User> findAllUserByUsername(String username) {

        return userPersistanceOutput.findAllUserByUsername(username);
    }

    @Override
    public Long getUserXp(String username) {
        return userPersistanceOutput.getUserXp(username);
    }

    @Override
    public String getCellphoneByUsername(String username) {
        return userPersistanceOutput.findUserByUsername(username).getCellPhone().getValue();
    }

    private void validateSearchUser(User searchUser, String username) {
        if (searchUser == null) {
            throw new UserNotFoundException(username);
        }
    }
}