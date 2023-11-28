package br.com.shapeup.adapters.output.repository.model.template;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity(name = "tb_email_html_template")
@Table
@Getter
@Setter
public class EmailHtmlTemplateModel {

    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    private String content;
    private String type;
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder(toBuilder = true)
    public EmailHtmlTemplateModel() { }
}
