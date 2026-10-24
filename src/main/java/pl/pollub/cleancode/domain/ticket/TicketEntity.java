package pl.pollub.cleancode.domain.ticket;

import jakarta.persistence.*;
import lombok.*;
import pl.pollub.cleancode.common.BaseEntity;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.domain.ticket.state.TicketState;
import pl.pollub.cleancode.domain.ticket.state.TicketStates;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity extends BaseEntity {
    @Column
    private String ticketNumber;

    @Column
    private String title;

    @Column(length = 2000)
    private String content;

    @Column
    @Enumerated(EnumType.STRING)
    private TicketPriority priority;

    @Column
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column
    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    //[State]
    public TicketState getState() {
        return TicketStates.of(status);
    }

    public void moveToNextState() {
        status = getState().next().getStatus();
    }

    public void close() {
        status = getState().close().getStatus();
    }

    public void reopen() {
        status = getState().reopen().getStatus();
    }

    public static Builder builder() {
        return new Builder();
    }

    //[Builder]
    public static class Builder {
        private String ticketNumber;
        private String title;
        private String content;
        private TicketPriority priority;
        private MessageType messageType;
        private TicketStatus status = TicketStatus.NEW;

        private Builder() {
        }

        public Builder ticketNumber(String ticketNumber) {
            this.ticketNumber = ticketNumber;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder priority(TicketPriority priority) {
            this.priority = priority;
            return this;
        }

        public Builder messageType(MessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public Builder status(TicketStatus status) {
            this.status = status;
            return this;
        }

        public TicketEntity build() {
            return new TicketEntity(ticketNumber, title, content, priority, messageType, status);
        }
    }
}
