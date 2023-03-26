package br.com.shapeup.adapters.output.repository.model.user;

import br.com.shapeup.adapters.output.repository.model.level.LevelEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

    @Id
    private UUID id = UUID.randomUUID();

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String username;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String cellPhone;

    @Column
    private String password;

    @Column
    @Temporal(TemporalType.DATE)
    private LocalDate birth;

    @Column
    private String biography;

    @Column
    private String profilePhotoUrl;

    @Column(columnDefinition = "BIT", nullable = false)
    private boolean isActive;

    @Column(name = "fk_level_id", insertable = false, updatable = false)
    private UUID fkLevelId;

    @OneToOne
    @JoinColumn(name = "fk_level_id", referencedColumnName = "id")
    private LevelEntity levelEntity;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
