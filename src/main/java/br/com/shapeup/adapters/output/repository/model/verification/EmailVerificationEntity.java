package br.com.shapeup.adapters.output.repository.model.verification;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
    String code;
    Boolean verified;
    String email;
    String username;
    @CreatedDate
    @Column(name = "created_at")
    LocalDateTime createdAt;
    @Column(name = "expires_in")
    LocalDateTime expiresIn;
    @LastModifiedDate
    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}
