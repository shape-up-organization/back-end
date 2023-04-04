package br.com.shapeup.adapters.input.web.controller.mapper.user;

import br.com.shapeup.adapters.input.web.controller.request.user.UserBiographyRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserBirthRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserCellphoneRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserLastNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserNameRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserPasswordRequest;
import br.com.shapeup.adapters.input.web.controller.request.auth.UserAuthRegisterRequest;
import br.com.shapeup.adapters.input.web.controller.request.user.UserRequest;
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

@Mapper(componentModel = "spring", uses = { UserHttpMapper.class })
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
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "birth", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "biography", ignore = true)
    User toUser(UserRequest userBiographyRequest);

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
