package pl.pollub.cleancode.domain.ticket;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//[UnitTest]
class TicketEntityTest {

    @Test
    void newTicketStartsInNewState() {
        TicketEntity ticket = TicketEntity.builder().title("Test").build();

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.NEW);
        assertThat(ticket.getState().getLabel()).isEqualTo("Nowe");
    }

    @Test
    void nextMovesThroughAllStatesInOrder() {
        TicketEntity ticket = TicketEntity.builder().build();

        ticket.moveToNextState();
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.IN_PROGRESS);
        ticket.moveToNextState();
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.RESOLVED);
        ticket.moveToNextState();
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.CLOSED);
    }

    @Test
    void closedTicketCannotMoveForward() {
        TicketEntity ticket = TicketEntity.builder().status(TicketStatus.CLOSED).build();

        assertThatThrownBy(ticket::moveToNextState)
                .isInstanceOf(IllegalTicketStateTransitionException.class)
                .hasMessageContaining("Zamknięte");
    }

    @Test
    void reopenIsAllowedOnlyForResolvedOrClosedTicket() {
        TicketEntity newTicket = TicketEntity.builder().build();
        TicketEntity resolvedTicket = TicketEntity.builder().status(TicketStatus.RESOLVED).build();

        assertThatThrownBy(newTicket::reopen).isInstanceOf(IllegalTicketStateTransitionException.class);
        resolvedTicket.reopen();
        assertThat(resolvedTicket.getStatus()).isEqualTo(TicketStatus.IN_PROGRESS);
    }

    @Test
    void ticketWithoutStatusFromOldDatabaseIsTreatedAsNew() {
        TicketEntity ticket = TicketEntity.builder().status(null).build();

        ticket.close();

        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.CLOSED);
    }
}
