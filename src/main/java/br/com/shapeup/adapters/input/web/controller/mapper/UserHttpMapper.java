package br.com.shapeup.adapters.input.web.controller.mapper;

import br.com.shapeup.adapters.input.web.controller.request.UserBiographyRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserBirthRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserCellphoneRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserEmailRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserLastNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserPasswordRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import java.text.ParseException;

@Mapper(componentModel = "spring", uses = { UserHttpMapper.class })
@Component
public interface UserHttpMapper {
    UserHttpMapper INSTANCE = Mappers.getMapper(UserHttpMapper.class);

    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(source = "cellPhone", target = "cellPhone", qualifiedByName = "stringToCellPhone")
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToPassword")
    @Mapping(source = "birth", target = "birth", qualifiedByName = "stringToBirth")
    User toUser(UserRequest userRequest);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "cellPhone", ignore = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(target = "birth", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToPassword")
    User toUser(UserPasswordRequest userPasswordRequest);

    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "cellPhone", ignore = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birth", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(source = "name", target = "name")
    User toUser(UserNameRequest userNameRequest);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "cellPhone", ignore = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birth", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(source = "lastName", target = "lastName")
    User toUser(UserLastNameRequest userLastNameRequest);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birth", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(source = "cellphone", target = "cellPhone", qualifiedByName = "stringToCellPhone")
    User toUser(UserCellphoneRequest userCellphoneRequest);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "cellPhone", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birth", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    User toUser(UserEmailRequest userEmailRequest);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "cellPhone", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(target = "biography", ignore = true)
    @Mapping(source = "birth", target = "birth", qualifiedByName = "stringToBirth")
    User toUser(UserBirthRequest userBirthRequest);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "cellPhone", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birth", ignore = true)
    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(source = "biography", target = "biography")
    User toUser(UserBiographyRequest userBiographyRequest);

    @Named("stringToEmail")
    public static Email stringToEmail(String email) {
        return Email.create(email);
    }

    @Named("emailToString")
    public static String emailToString(Email email) {
        return email.getValue();
    }

    @Named("stringToCellPhone")
    public static CellPhone stringToCellPhone(String cellPhone) {
        return CellPhone.create(cellPhone);
    }

    @Named("stringToPassword")
    public static Password stringToPassword(String password) {
        return Password.create(password);
    }

    @Named("passwordToString")
    public static String stringToPassword(Password password) {
        return password.getValue();
    }

    @Named("stringToBirth")
    public static Birth stringToBirth(String birth) throws ParseException {
        return Birth.create(birth);
    }
}
