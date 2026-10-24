package pl.pollub.cleancode.domain.ticket;

import java.util.List;

//[ISP]
public interface TicketReader {
    List<TicketEntity> findAllNewestFirst();

    TicketEntity findById(Long ticketId);
}
