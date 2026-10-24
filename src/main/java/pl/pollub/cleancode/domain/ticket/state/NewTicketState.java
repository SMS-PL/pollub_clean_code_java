package pl.pollub.cleancode.domain.ticket.state;

import pl.pollub.cleancode.domain.ticket.IllegalTicketStateTransitionException;
import pl.pollub.cleancode.domain.ticket.TicketStatus;

//[State]
public class NewTicketState implements TicketState {
    @Override
    public TicketStatus getStatus() {
        return TicketStatus.NEW;
    }

    @Override
    public String getLabel() {
        return "Nowe";
    }

    @Override
    public TicketState next() {
        return new InProgressTicketState();
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
