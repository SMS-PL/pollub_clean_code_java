package pl.pollub.cleancode.domain.ticket.state;

import pl.pollub.cleancode.domain.ticket.TicketStatus;

//[State]
public interface TicketState {
    TicketStatus getStatus();

    String getLabel();

    TicketState next();

    TicketState close();

    TicketState reopen();
}
