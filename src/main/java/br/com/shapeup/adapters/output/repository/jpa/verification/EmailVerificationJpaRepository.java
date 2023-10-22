package br.com.shapeup.adapters.output.repository.jpa.verification;

import br.com.shapeup.adapters.output.repository.model.verification.EmailVerificationEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailVerificationJpaRepository extends JpaRepository<EmailVerificationEntity, UUID> {
    Optional<EmailVerificationEntity> findByEmail(String email);

    @Query("SELECT verified FROM EmailVerificationEntity WHERE username = :email")
    Boolean findVerifiedByEmail(@Param("email") String email);
}
