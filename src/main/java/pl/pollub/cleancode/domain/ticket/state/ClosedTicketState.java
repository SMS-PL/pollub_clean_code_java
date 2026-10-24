package pl.pollub.cleancode.domain.ticket.state;

import pl.pollub.cleancode.domain.ticket.IllegalTicketStateTransitionException;
import pl.pollub.cleancode.domain.ticket.TicketStatus;

//[State]
public class ClosedTicketState implements TicketState {
    @Override
    public TicketStatus getStatus() {
        return TicketStatus.CLOSED;
    }

    @Override
    public String getLabel() {
        return "Zamknięte";
    }

    @Override
    public TicketState next() {
        throw new IllegalTicketStateTransitionException(getLabel(), "dalej");
    }

    @Override
    public TicketState close() {
        throw new IllegalTicketStateTransitionException(getLabel(), "zamknij");
    }

    @Override
    public TicketState reopen() {
        return new InProgressTicketState();
    }
}
