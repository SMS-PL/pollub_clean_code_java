package pl.pollub.cleancode.domain.inquiry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

//Wiadomosc wychdoza z naszego systemu np do banku
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    private String content;
    private InquiryStatus status;
    private String email;
}
