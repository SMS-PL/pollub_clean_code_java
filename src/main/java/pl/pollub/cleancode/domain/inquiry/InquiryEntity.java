package pl.pollub.cleancode.domain.inquiry;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//Wiadomosc wychodzaca z naszego systemu np do banku
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ticketId;
    @Column(length = 2000)
    private String content;
    private InquiryStatus status;
    //[Naming]
    private String emailAddress;

    public static Builder builder() {
        return new Builder();
    }

    //[Builder]
    public static class Builder {
        private Long ticketId;
        private String content;
        private InquiryStatus status;
        private String emailAddress;

        private Builder() {
        }

        public Builder ticketId(Long ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder status(InquiryStatus status) {
            this.status = status;
            return this;
        }

        public Builder emailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public InquiryEntity build() {
            return new InquiryEntity(null, ticketId, content, status, emailAddress);
        }
    }
}
