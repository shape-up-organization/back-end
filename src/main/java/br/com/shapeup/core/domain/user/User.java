package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Entity<UserId> {

    private FullName fullName;
    private String username;
    private Email email;
    private CellPhone cellPhone;
    private Password password;
    private Birth birth;
    private String biography;
    private Long xp;
    private String profilePicture;
    private String originalPassword;
    private Boolean isActive;
    private List<User> friends = new ArrayList<>();
    private List<Training> trainings = new ArrayList<>();

    public User(UserId id, String name, String lastName, String username, Email email, CellPhone cellPhone, Password password,
                Birth birth, String biography, Long xp, String profilePicture, String originalPassword, Boolean isActive) {
        super(id);
        this.fullName = FullName.create(name, lastName);
        this.username = username;
        this.email = email;
        this.cellPhone = cellPhone;
        this.password = password;
        this.birth = birth;
        this.biography = biography;
        this.xp = xp;
        this.profilePicture = profilePicture;
        this.originalPassword = originalPassword;
        this.isActive = isActive;
    }

    public static User newUser(UUID anId, String name, String lastName, String username, Email email, CellPhone cellPhone,
                               Password password, Birth birth, String biography, Long xp, String profilePicture, String originalPassword, Boolean isActive) {
        var id = UserId.from(anId);
        return new User(id, name, lastName, username, email, cellPhone, password, birth, biography, xp, profilePicture, originalPassword, isActive);
    }

    private void updateXp(Long xp) {
        this.xp = xp;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserValidator(handler, this).validate();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(getFullName(), user.getFullName()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getCellPhone(), user.getCellPhone()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getBirth(), user.getBirth()) && Objects.equals(getBiography(), user.getBiography()) && Objects.equals(getXp(), user.getXp()) && Objects.equals(getProfilePicture(), user.getProfilePicture()) && Objects.equals(getFriends(), user.getFriends());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                getFullName(),
                getUsername(),
                getEmail(),
                getCellPhone(),
                getPassword(),
                getBirth(),
                getBiography(),
                getXp(),
                getProfilePicture(),
                getFriends());
    }
}
