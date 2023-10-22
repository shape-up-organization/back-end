package br.com.shapeup.adapters.output.repository.model.user;

import br.com.shapeup.adapters.output.repository.model.friend.FriendsEntity;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tb_user", indexes = @Index(columnList = "username", name = "idx_username"))
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID id;

    @Column
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column
    private String email;

    @Column
    private String cellPhone;

    @Column
    private String password;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate birth;

    @Column
    private String biography;

    @Column
    private String profilePicture;

    @Column(columnDefinition = "int8 default 0")
    private Long xp;

    @Column(columnDefinition = "boolean default false")
    private boolean isActive = false;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @OneToMany(mappedBy = "userSender")
    private List<FriendsEntity> friendsSender = new ArrayList<>();

    @OneToMany(mappedBy = "userReceiver")
    private List<FriendsEntity> friendsReceiver = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "tb_training_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false),
            inverseJoinColumns = @JoinColumn(name = "training_id", referencedColumnName = "training_id", updatable = false)
    )
    private List<TrainingEntity> trainings;

    public UserEntity(
            String name,
            String lastName,
            String username,
            String profilePicture,
            Long xp
    ) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.profilePicture = profilePicture;
        this.xp = xp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return isActive() == that.isActive() && Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getLastName(), that.getLastName()) && Objects.equals(getFullName(), that.getFullName()) && Objects.equals(getUsername(), that.getUsername()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getCellPhone(), that.getCellPhone()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getBirth(), that.getBirth()) && Objects.equals(getBiography(), that.getBiography()) && Objects.equals(getProfilePicture(), that.getProfilePicture()) && Objects.equals(getXp(), that.getXp()) && Objects.equals(getRoles(), that.getRoles()) && Objects.equals(getFriendsSender(), that.getFriendsSender()) && Objects.equals(getFriendsReceiver(), that.getFriendsReceiver());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLastName(), getFullName(), getUsername(), getEmail(), getCellPhone(), getPassword(), getBirth(), getBiography(), getProfilePicture(), getXp(), isActive(), getRoles(), getFriendsSender(), getFriendsReceiver());
    }
}
