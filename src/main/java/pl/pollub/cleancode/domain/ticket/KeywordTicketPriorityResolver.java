package pl.pollub.cleancode.domain.ticket;

import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;

import java.util.List;
import java.util.Optional;

//[LSP]
public class KeywordTicketPriorityResolver extends TicketPriorityResolver {
    //[MagicNumber]
    private static final List<String> URGENT_KEYWORDS = List.of("pilne", "pilnie", "urgent", "natychmiast");
    private static final int LONG_MESSAGE_LENGTH = 200;

    //[Lambda]
    private final List<PriorityRule> rules = List.of(
            request -> containsUrgentKeyword(request.getMessage())
                    ? Optional.of(TicketPriority.HIGH) : Optional.empty(),
            request -> request.getMessage() != null && request.getMessage().length() > LONG_MESSAGE_LENGTH
                    ? Optional.of(TicketPriority.MEDIUM) : Optional.empty()
    );

    //[Stream]
    @Override
    public TicketPriority resolve(MessageRequestDto request) {
        return rules.stream()
                .map(rule -> rule.resolve(request))
                .flatMap(Optional::stream)
                .findFirst()
                .orElseGet(() -> super.resolve(request));
    }

    // klasa bazowa nie czyta treści, więc przyjmuje też null - pochodna nie może tego wymagania zaostrzyć
    private boolean containsUrgentKeyword(String message) {
        if (message == null) {
            return false;
        }
        String lowerCaseMessage = message.toLowerCase();
        return URGENT_KEYWORDS.stream().anyMatch(lowerCaseMessage::contains);
    }
}
