package br.com.shapeup.adapters.output.repository.jpa.verification;

import br.com.shapeup.adapters.output.repository.model.verification.ResetPasswordVerificationEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordVerificationRepository extends JpaRepository<ResetPasswordVerificationEntity, UUID> {
    ResetPasswordVerificationEntity findByEmail(String email);
}
