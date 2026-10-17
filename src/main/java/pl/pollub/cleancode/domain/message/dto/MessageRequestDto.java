package pl.pollub.cleancode.domain.message.dto;

import lombok.*;
import pl.pollub.cleancode.domain.message.enums.MessageType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    private String message;
    private MessageType type;
}
