package pl.pollub.cleancode.domain.message.exception;

import pl.pollub.cleancode.common.DomainException;

public class MessageProcessingException extends DomainException {
    public MessageProcessingException(String reason) {
        super("Błąd podczas zapisu wiadomości: " + reason);
    }
}
