package br.com.shapeup.core.domain.user;

import br.com.shapeup.common.domain.Entity;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.domain.validation.ValidationHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
    private List<User> friends = new ArrayList<>();
    private List<Training> trainings = new ArrayList<>();

    public User(UserId id, String name, String lastName, String username, Email email, CellPhone cellPhone, Password password,
            Birth birth, String biography, Long xp, String profilePicture) {
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
    }

    public static User newUser(UUID anId, String name, String lastName, String username, Email email, CellPhone cellPhone,
            Password password, Birth birth, String biography, Long xp, String profilePicture) {
        var id = UserId.from(anId);
        return new User(id, name, lastName, username, email, cellPhone, password, birth, biography, xp, profilePicture);
    }

    private void updateXp(Long xp) {
        this.xp = xp;
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new UserValidator(handler, this).validate();
    }

    public UserId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public CellPhone getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(CellPhone cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    public Birth getBirth() {
        return birth;
    }

    public void setBirth(Birth birth) {
        this.birth = birth;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
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
