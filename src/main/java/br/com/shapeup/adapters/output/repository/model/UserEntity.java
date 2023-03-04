package br.com.shapeup.adapters.output.repository.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    private UUID id = UUID.randomUUID();
    @Column
    private String name;
    @Column
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String cellPhone;

    @Column
    private String password;
    @Column
    @Temporal(TemporalType.DATE)
    private Date birth;
}
