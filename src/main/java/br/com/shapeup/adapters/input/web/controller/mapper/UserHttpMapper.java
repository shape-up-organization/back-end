package br.com.shapeup.adapters.input.web.controller.mapper;

import br.com.shapeup.adapters.input.web.controller.request.UserPasswordRequest;
import br.com.shapeup.adapters.input.web.controller.request.UserRequest;
import br.com.shapeup.core.domain.user.CellPhone;
import br.com.shapeup.core.domain.user.Email;
import br.com.shapeup.core.domain.user.Password;
import br.com.shapeup.core.domain.user.User;
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
    User toUser(UserRequest userRequest);

    @Mapping(target = "name", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "cellPhone", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(source = "password", target = "password", qualifiedByName = "stringToPassword")
    User toUser(UserPasswordRequest userPasswordRequest);

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
}
