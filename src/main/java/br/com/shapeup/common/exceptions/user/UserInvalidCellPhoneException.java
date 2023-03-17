package br.com.shapeup.common.exceptions.user;

public class UserInvalidCellPhoneException extends RuntimeException {
    public UserInvalidCellPhoneException() {
        super("invalid cellphone!");
    }

    public UserInvalidCellPhoneException(String message) {
        super(message);
    }
}
