package br.com.shapeup.adapters.output.integration.user;

import br.com.shapeup.adapters.output.repository.jpa.UserRepositoryJpa;
import br.com.shapeup.adapters.output.repository.mapper.UserMapper;
import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.common.exceptions.user.UserExistsByEmailException;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.factories.UserEntityFactory;
import br.com.shapeup.factories.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {
    @Mock
    private UserRepositoryJpa userRepositoryJpa;
    private UserMapper userMapper;
    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

    @BeforeEach
    void setUp() {
        userMapper = UserMapper.INSTANCE;
        userPersistenceAdapter = new UserPersistenceAdapter(userRepositoryJpa, userMapper);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Given a valid user when create account then return success")
    @Disabled
    void saveWithSuccess() {
        User userMock = UserFactory.getInstance().create();

        Mockito.when(userRepositoryJpa.existsByEmail(userMock.getEmail().getValue())).thenReturn(false);
        userPersistenceAdapter.save(userMock);

        Mockito.verify(userRepositoryJpa, Mockito.times(1)).save(Mockito.any(UserEntity.class));
    }

    @Test
    @DisplayName("Given a valid user when create account the return an user exists exception")
    @Disabled
    void saveWithException() {
        User userMock = UserFactory.getInstance().create();
        UserEntity userEntityMock = UserEntityFactory.getInstance().create();

        Mockito.when(userRepositoryJpa.existsByEmail(userMock.getEmail().getValue())).thenReturn(true);

        UserExistsByEmailException exception = Assertions.assertThrows(UserExistsByEmailException.class, () -> userPersistenceAdapter.save(userMock));
        Assertions.assertTrue(exception.getMessage().contains(userMock.getEmail().getValue()));

        Mockito.verify(userRepositoryJpa, Mockito.never()).save(userEntityMock);
    }
}
