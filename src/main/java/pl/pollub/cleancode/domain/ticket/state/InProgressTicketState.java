package pl.pollub.cleancode.domain.ticket.state;

import pl.pollub.cleancode.domain.ticket.IllegalTicketStateTransitionException;
import pl.pollub.cleancode.domain.ticket.TicketStatus;

//[State]
public class InProgressTicketState implements TicketState {
    @Override
    public TicketStatus getStatus() {
        return TicketStatus.IN_PROGRESS;
    }

    @Override
    public String getLabel() {
        return "W realizacji";
    }

    @Override
    public TicketState next() {
        return new ResolvedTicketState();
    }

    @Override
    public TicketState close() {
        return new ClosedTicketState();
    }

    @Override
    public TicketState reopen() {
        throw new IllegalTicketStateTransitionException(getLabel(), "otwórz ponownie");
    }
}
