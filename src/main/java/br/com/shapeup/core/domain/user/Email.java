package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.ValueObject;
import br.com.shapeup.common.exceptions.user.UserInvalidEmailException;

public class Email extends ValueObject {
    private String value;

    private Email(String value) {
        this.value = value;
    }

    public static Email create(String email) {
        return new Email(email);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void validateEmail(){
        if(!value.matches("^[A-Za-z0-9+_.-]+@(.+)$")){
            throw new UserInvalidEmailException("email should contain @ and .");
        }

        if(value.length() > 255){
            throw new UserInvalidEmailException("email should contain less than 255 characters");
        }
        if(value.length() < 5){
            throw new UserInvalidEmailException("email should contain more than 5 characters");
        }
    }
}
