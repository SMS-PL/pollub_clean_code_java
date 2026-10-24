package pl.pollub.cleancode.domain.ticket;

import pl.pollub.cleancode.common.DomainException;

//[Exception]
public class IllegalTicketStateTransitionException extends DomainException {
    public IllegalTicketStateTransitionException(String currentStateLabel, String actionName) {
        super("Nie można wykonać akcji '" + actionName + "' dla zgłoszenia w stanie '" + currentStateLabel + "'");
    }
}
