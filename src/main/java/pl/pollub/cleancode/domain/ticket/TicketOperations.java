package pl.pollub.cleancode.domain.ticket;

import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;

import java.util.List;

//[ISP]
@Deprecated
public interface TicketOperations {
    TicketEntity createTicket(MessageRequestDto request);

    List<TicketEntity> findAllNewestFirst();

    TicketEntity findById(Long ticketId);

    void moveToNextState(Long ticketId);

    void close(Long ticketId);

    void reopen(Long ticketId);

    void delete(Long ticketId);

    String exportToPdf(Long ticketId);
}
