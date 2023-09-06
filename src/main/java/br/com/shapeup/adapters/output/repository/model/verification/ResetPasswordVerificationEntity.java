package br.com.shapeup.adapters.output.repository.model.verification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

@Entity
@Table(name = "tb_reset_password_verification")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ResetPasswordVerificationEntity {
    @Id
    private UUID id;
    private String code;
    @Column(name = "user_id")
    private String userId;
    private String status;
    private Boolean verified;
    private String email;
    @CreatedDate
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    @Column(name = "expires_in")
    private OffsetDateTime expiresIn;
    @LastModifiedDate
    @Field("updated_at")
    OffsetDateTime updatedAt;
}
