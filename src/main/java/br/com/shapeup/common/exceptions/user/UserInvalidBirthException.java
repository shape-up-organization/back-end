package br.com.shapeup.common.exceptions.user;

public class UserInvalidBirthException extends RuntimeException {
    public UserInvalidBirthException(String birth) {
        super(String.format("%s is an invalid birth", birth));
    }

    public UserInvalidBirthException() {
        super("user should be older than 18 years");
    }
}
