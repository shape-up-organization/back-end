package br.com.shapeup.adapters.output.repository.mapper;

import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.user.UserId;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
@Component
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", source = "id", qualifiedByName = "uuidToUserId")
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(source = "cellPhone", target = "cellPhone", qualifiedByName = "stringToCellphone")
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToPassword")
    User userEntitytoUser(UserEntity userEntity);

    @Mapping(target = "id", source = "id", qualifiedByName = "userIdToUuid")
    @Mapping(source = "email", target = "email", qualifiedByName = "emailToString")
    @Mapping(source = "password", target = "password", qualifiedByName = "passwordToString")
    @Mapping(source = "cellPhone", target = "cellPhone", qualifiedByName = "cellPhoneToString")
    UserEntity userToUserEntity(User user);

    default Email toEmail(String email) {
        return new Email(email);
    }

    @Named("uuidToUserId")
    public static UserId uuidToUserId(UUID uuid) {
        return UserId.from(uuid);
    }
    @Named("userIdToUuid")
    public static UUID userIdToUuid(UserId userId) {
        return UUID.fromString(userId.getValue());
    }

    @Named("emailToString")
    public static String emailToString(Email email) {
        return email.getValue();
    }

    @Named(("stringToEmail"))
    public static Email stringToEmail(String email) {
        return new Email(email);
    }


    @Named("cellPhoneToString")
    public static String cellPhoneToString(CellPhone cellPhone) {
        return cellPhone.getValue();
    }

    @Named("stringToCellphone")
    public static CellPhone stringToCellphone(String cellPhone) {
        return new CellPhone(cellPhone);
    }

    @Named("passwordToString")
    public static String passwordToString(Password password) {
        return password.getValue();
    }

    @Named("stringToPassword")
    public static Password toPassword(String password) {
        return new Password(password);
    }
}
