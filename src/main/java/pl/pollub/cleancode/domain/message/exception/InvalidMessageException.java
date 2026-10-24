package pl.pollub.cleancode.domain.message.exception;

import pl.pollub.cleancode.common.DomainException;

//[Exception]
public class InvalidMessageException extends DomainException {
    public InvalidMessageException(String reason) {
        super("Niepoprawna wiadomość: " + reason);
    }
}
