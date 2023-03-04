package br.com.shapeup.common.exceptions.user;

public class UserExistsByEmailException extends RuntimeException {
    public UserExistsByEmailException(String email) {
        super(String.format("User with email %s does not exists.", email));
    }
}
