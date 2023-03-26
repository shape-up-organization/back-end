package br.com.shapeup.adapters.output.repository.model.block;

import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_block_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockUsersEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column
    private String fkUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column
    private String fkBlockUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_block_user_id", referencedColumnName = "id")
    private UserEntity userFollowerEntity;
}
