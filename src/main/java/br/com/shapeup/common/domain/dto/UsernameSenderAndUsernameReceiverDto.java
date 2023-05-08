package br.com.shapeup.common.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernameSenderAndUsernameReceiverDto{
    private String usernameSender;
    private String usernameReceiver;
}
