package pl.pollub.cleancode.domain.ticket;

import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;

import java.util.Optional;

//[FunctionalInterface]
@FunctionalInterface
public interface PriorityRule {
    Optional<TicketPriority> resolve(MessageRequestDto request);
}
