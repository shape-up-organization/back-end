package br.com.shapeup.common.exceptions.user;

public class UserExistsByEmailException extends RuntimeException {
    public UserExistsByEmailException() {
        super("User credentials are not valid");
    }
}
