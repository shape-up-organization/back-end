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

@Entity
@Table(name = "tb_email_verification")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmailVerificationEntity {
    @Id
    UUID id;
    @Column(name = "user_id")
    String userId;
    String code;
    Boolean verified;
    String email;
    String username;
    @CreatedDate
    @Column(name = "created_at")
    OffsetDateTime createdAt;
    @Column(name = "expires_in")
    OffsetDateTime expiresIn;
    @LastModifiedDate
    @Column(name = "updated_at")
    OffsetDateTime updatedAt;
}
