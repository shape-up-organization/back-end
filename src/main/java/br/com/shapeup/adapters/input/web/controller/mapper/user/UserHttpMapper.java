package br.com.shapeup.adapters.input.web.controller.mapper.user;

import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserBiographyRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserBirthRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserCellphoneRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserLastNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserPasswordRequest;
import br.com.shapeup.core.domain.user.Birth;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
import java.text.ParseException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "cdi")
@Component
public interface UserHttpMapper {
    UserHttpMapper INSTANCE = Mappers.getMapper(UserHttpMapper.class);

    @Mapping(source = "email", target = "email", qualifiedByName = "stringToEmail")
    @Mapping(source = "cellPhone", target = "cellPhone", qualifiedByName = "stringToCellPhone")
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToPassword")
    @Mapping(source = "birth", target = "birth", qualifiedByName = "stringToBirth")
    User toUser(UserAuthRegisterRequest userAuthRegisterRequest);

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
    @Mapping(target = "email", source = "email", qualifiedByName = "stringToEmail")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birth", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(target = "lastName", source = "lastName")
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
    static Email stringToEmail(String email) {
        return Email.create(email);
    }

    @Named("emailToString")
    static String emailToString(Email email) {
        return email.getValue();
    }

    @Named("stringToCellPhone")
    static CellPhone stringToCellPhone(String cellPhone) {
        return CellPhone.create(cellPhone);
    }

    @Named("stringToPassword")
    static Password stringToPassword(String password) {
        return Password.create(password);
    }

    @Named("passwordToString")
    static String stringToPassword(Password password) {
        return password.getValue();
    }

    @Named("stringToBirth")
    static Birth stringToBirth(String birth) throws ParseException {
        return Birth.create(birth);
    }
}
