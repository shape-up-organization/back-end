package br.com.shapeup.adapters.output.repository.jpa.template;

import br.com.shapeup.adapters.output.repository.model.template.EmailHtmlTemplateModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailHtmlTemplateModel, UUID> {

    Optional<EmailHtmlTemplateModel> findByType(String type);
}
