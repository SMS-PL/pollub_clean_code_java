package pl.pollub.cleancode.domain.ticket;

import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;

//[ISP]
public interface TicketCreator {
    TicketEntity createTicket(MessageRequestDto request);
}
