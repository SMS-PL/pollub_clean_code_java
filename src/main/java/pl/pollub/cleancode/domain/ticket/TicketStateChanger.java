package pl.pollub.cleancode.domain.ticket;

//[ISP]
public interface TicketStateChanger {
    void moveToNextState(Long ticketId);

    void close(Long ticketId);

    void reopen(Long ticketId);
}
