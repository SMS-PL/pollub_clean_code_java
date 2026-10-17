package pl.pollub.cleancode.domain.message.entity;

import jakarta.persistence.*;
import lombok.*;
import pl.pollub.cleancode.domain.message.enums.MessageType;

@Entity
@Table(name = "messages")
@Getter
@Setter
@Builder
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
}
