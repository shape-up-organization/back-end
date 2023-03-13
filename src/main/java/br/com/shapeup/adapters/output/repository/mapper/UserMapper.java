package br.com.shapeup.adapters.output.repository.mapper;

import br.com.shapeup.adapters.output.repository.model.UserEntity;
import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.domain.user.UserId;
import java.time.LocalDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
@Component
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(source = "cellPhone", target = "cellPhone", qualifiedByName = "stringToCellphone")
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToPassword")
    @Mapping(source = "birth", target = "birth", qualifiedByName = "dateToBirth")
    User userEntitytoUser(UserEntity userEntity);

    @Mapping(source = "id", target = "id", qualifiedByName = "userIdToUuid")
    @Mapping(source = "email", target = "email", qualifiedByName = "emailToString")
    @Mapping(source = "password", target = "password", qualifiedByName = "passwordToString")
    @Mapping(source = "cellPhone", target = "cellPhone", qualifiedByName = "cellPhoneToString")
    @Mapping(source = "birth", target = "birth", qualifiedByName = "birthToDate")
    UserEntity userToUserEntity(User user);

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
        return Email.create(email);
    }

    @Named("cellPhoneToString")
    public static String cellPhoneToString(CellPhone cellPhone) {
        return cellPhone.getValue();
    }

    @Named("stringToCellphone")
    public static CellPhone stringToCellphone(String cellPhone) {
        return CellPhone.create(cellPhone);
    }

    @Named("passwordToString")
    public static String passwordToString(Password password) {
        return password.getValue();
    }

    @Named("stringToPassword")
    public static Password toPassword(String password) {
        return Password.create(password);
    }

    @Named("birthToDate")
    public static LocalDate birthToDate(Birth birth) {
        return birth.getValue();
    }

    @Named("dateToBirth")
    public static Birth dateToBirth(LocalDate date) {
        return Birth.create(date);
    }
}
