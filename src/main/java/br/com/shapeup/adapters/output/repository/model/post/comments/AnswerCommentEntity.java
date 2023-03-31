package br.com.shapeup.adapters.output.repository.model.post.comments;

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

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_answer_comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerCommentEntity {
    @Id
    private UUID id = UUID.randomUUID();

    @Column(length = 1000)
    private String commentMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_comment_id", referencedColumnName = "id")
    private CommentEntity commentEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnswerCommentEntity that = (AnswerCommentEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
