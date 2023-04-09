package br.com.shapeup.adapters.output.repository.model.chat;


import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Message {
    private String senderName;
    private String receiverName;
    @Nullable
    private String message;
    @Nullable
    private LocalDateTime date = LocalDateTime.now();
    @Enumerated
    private Status status;
}
