//package br.com.shapeup.adapters.output.integration.user;
//
//import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipJpaRepository;
//import br.com.shapeup.adapters.output.repository.jpa.friend.FriendshipMongoRepository;
//import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
//import br.com.shapeup.adapters.output.repository.mapper.user.UserMapper;
//import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
//import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
//import br.com.shapeup.core.domain.user.User;
//import br.com.shapeup.core.ports.output.friend.FindFriendshipOutput;
//import br.com.shapeup.core.ports.output.user.FindUserOutput;
//import br.com.shapeup.factories.UserEntityFactory;
//import br.com.shapeup.factories.UserFactory;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.junit.jupiter.MockitoSettings;
//import org.mockito.quality.Strictness;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@MockitoSettings(strictness = Strictness.LENIENT)
//@ExtendWith(MockitoExtension.class)
//class UserPersistenceAdapterTest {
//
//    @InjectMocks
//    private UserPersistenceAdapter userPersistenceAdapter;
//    @Mock
//    private UserJpaRepository UserJpaRepository;
//    @Mock
//    private FindFriendshipOutput findFriendshipOutput;
//    @Mock
//    private FindUserOutput findUserOutput;
//    @Mock
//    private FriendshipJpaRepository friendshipJpaRepository;
//    @Mock
//    private FriendshipMongoRepository friendshipMongoRepository;
//    private UserMapper userMapper;
//
//
//    @BeforeEach
//    void setUp() {
//        userPersistenceAdapter = new UserPersistenceAdapter(UserJpaRepository,
//                userMapper,
//                findFriendshipOutput,
//                findUserOutput,
//                friendshipJpaRepository,
//                friendshipMongoRepository);
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("Given a valid user when create account then return success")
//    void saveWithSuccess() {
//        User userMock = UserFactory.getInstance().create();
//
//        Mockito.when(UserJpaRepository.existsByEmail(userMock.getEmail().getValue())).thenReturn(false);
//        userPersistenceAdapter.save(userMock);
//
//        Mockito.verify(UserJpaRepository, Mockito.times(1)).save(Mockito.any(UserEntity.class));
//    }
//
//    @Test
//    @DisplayName("Given a valid user when create account the return an user exists exception")
//    void saveWithException() {
//        User userMock = UserFactory.getInstance().create();
//        UserEntity userEntityMock = UserEntityFactory.getInstance().create();
//
//        Mockito.when(UserJpaRepository.existsByEmail(userMock.getEmail().getValue())).thenReturn(true);
//
//        UserExistsByEmailException exception = Assertions.assertThrows(UserExistsByEmailException.class,
//                () -> userPersistenceAdapter.save(userMock));
//        Assertions.assertTrue(exception.getMessage().contains(userMock.getEmail().getValue()));
//
//        Mockito.verify(UserJpaRepository, Mockito.never()).save(userEntityMock);
//    }
//}
