package pl.pollub.cleancode.domain.message.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.pollub.cleancode.domain.message.enums.MessageType;

@Entity
@Table(name = "messages")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String message;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    public static Builder builder() {
        return new Builder();
    }

    //[Builder]
    public static class Builder {
        private String message;
        private MessageType messageType;

        private Builder() {
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder messageType(MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public Message build() {
            return new Message(null, message, messageType);
        }
    }
}
