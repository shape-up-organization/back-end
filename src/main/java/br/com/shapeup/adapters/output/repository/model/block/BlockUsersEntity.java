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
    private UUID id = UUID.randomUUID();

    @Column(name = "fk_user_id", insertable = false, updatable = false)
    private UUID fkUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @Column(name = "fk_block_user_id", insertable = false, updatable = false)
    private UUID fkBlockUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_block_user_id", referencedColumnName = "id")
    private UserEntity userBlock;
}
