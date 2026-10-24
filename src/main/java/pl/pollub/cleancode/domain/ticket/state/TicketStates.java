package pl.pollub.cleancode.domain.ticket.state;

import lombok.experimental.UtilityClass;
import pl.pollub.cleancode.domain.ticket.TicketStatus;

//[State]
@UtilityClass
public class TicketStates {

    // stare zgłoszenia z bazy nie mają statusu - traktujemy je jak nowe
    public static TicketState of(TicketStatus status) {
        if (status == null) {
            return new NewTicketState();
        }
        return switch (status) {
            case NEW -> new NewTicketState();
            case IN_PROGRESS -> new InProgressTicketState();
            case RESOLVED -> new ResolvedTicketState();
            case CLOSED -> new ClosedTicketState();
        };
    }
}
