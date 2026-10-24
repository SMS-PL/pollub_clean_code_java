package pl.pollub.cleancode.domain.ticket;

import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;

//[LSP]
public class TicketPriorityResolver {

    public TicketPriority resolve(MessageRequestDto request) {
        if (request.getType() == MessageType.PAYMENT) {
            return TicketPriority.HIGH;
        }
        if (request.getType() == MessageType.SHIPPING) {
            return TicketPriority.MEDIUM;
        }
        return TicketPriority.LOW;
    }
}
