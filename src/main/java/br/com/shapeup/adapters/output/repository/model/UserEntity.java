package br.com.shapeup.adapters.output.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;
        @Column
        private String name;
        @Column
        private String lastName;
        @Column
        private String email;
        @Column
        private String cellPhone;
        @Column
        private String password;
}
