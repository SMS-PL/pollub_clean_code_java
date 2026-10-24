package pl.pollub.cleancode.domain.message.dto;

import lombok.*;
import pl.pollub.cleancode.domain.message.enums.MessageType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageRequestDto {
    private String message;
    private MessageType type;

    public static Builder builder() {
        return new Builder();
    }

    //[Builder]
    public static class Builder {
        private String message;
        private MessageType type;

        private Builder() {
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder type(MessageType type) {
            this.type = type;
            return this;
        }

        public MessageRequestDto build() {
            return new MessageRequestDto(message, type);
        }
    }
}
