package pl.pollub.cleancode.domain.ticket;

//[LSP] //[Naming]
public class TicketNumberGenerator {
    protected static final String TICKET_NUMBER_PREFIX = "TICKET-";

    public String generate() {
        return TICKET_NUMBER_PREFIX + System.currentTimeMillis();
    }
}
