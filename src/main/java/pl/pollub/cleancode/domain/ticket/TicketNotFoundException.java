package pl.pollub.cleancode.domain.ticket;

import pl.pollub.cleancode.common.DomainException;

public class TicketNotFoundException extends DomainException {
    public TicketNotFoundException(Long ticketId) {
        super("Nie znaleziono zgłoszenia o id " + ticketId);
    }
}
