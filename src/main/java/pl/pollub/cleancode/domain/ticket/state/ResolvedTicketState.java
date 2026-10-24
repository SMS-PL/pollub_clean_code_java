package pl.pollub.cleancode.domain.ticket.state;

import pl.pollub.cleancode.domain.ticket.TicketStatus;

//[State]
public class ResolvedTicketState implements TicketState {
    @Override
    public TicketStatus getStatus() {
        return TicketStatus.RESOLVED;
    }

    @Override
    public String getLabel() {
        return "Rozwiązane";
    }

    @Override
    public TicketState next() {
        return new ClosedTicketState();
    }

    @Override
    public TicketState close() {
        return new ClosedTicketState();
    }

    @Override
    public TicketState reopen() {
        return new InProgressTicketState();
    }
}
