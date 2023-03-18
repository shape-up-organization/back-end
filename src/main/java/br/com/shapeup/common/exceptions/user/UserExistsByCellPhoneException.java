package br.com.shapeup.common.exceptions.user;

public class UserExistsByCellPhoneException extends RuntimeException{
    public UserExistsByCellPhoneException(String cellPhone) {
        super(String.format("User with cellphone %s already exists!", cellPhone));
    }
}
