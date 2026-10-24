package pl.pollub.cleancode.domain.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.exception.UnsupportedMessageTypeException;

//[Factory]
@Component
@RequiredArgsConstructor
public class TicketFactory {
    private final TicketNumberGenerator ticketNumberGenerator;
    private final TicketPriorityResolver ticketPriorityResolver;

    public TicketEntity createTicket(MessageRequestDto request) {
        return switch (request.getType()) {
            case PAYMENT -> buildTicket(request, "[Płatność] Problem z płatnością");
            case SHIPPING -> buildTicket(request, "[Wysyłka] Problem z dostawą");
            //[Exception]
            default -> throw new UnsupportedMessageTypeException(request.getType());
        };
    }

    private TicketEntity buildTicket(MessageRequestDto request, String title) {
        return TicketEntity.builder()
                .ticketNumber(ticketNumberGenerator.generate())
                .title(title)
                .content(request.getMessage())
                .priority(ticketPriorityResolver.resolve(request))
                .messageType(request.getType())
                .status(TicketStatus.NEW)
                .build();
    }
}
