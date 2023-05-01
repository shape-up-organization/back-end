package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.exceptions.user.UserInvalidLastName;
import br.com.shapeup.common.exceptions.user.UserInvalidNameException;
import br.com.shapeup.common.exceptions.user.UserInvalidPasswordException;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import br.com.shapeup.core.domain.validation.Validator;

public class UserValidator extends Validator {
    private final User user;

    protected UserValidator(ValidationHandler anHandler, User user) {
        super(anHandler);
        this.user = user;
    }

    @Override
    public void validate() {
        user.getFullName().validateLastName();
    }
}
