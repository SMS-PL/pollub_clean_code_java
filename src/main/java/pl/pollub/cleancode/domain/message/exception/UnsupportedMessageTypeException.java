package pl.pollub.cleancode.domain.message.exception;

import pl.pollub.cleancode.common.DomainException;
import pl.pollub.cleancode.domain.message.enums.MessageType;

//[Exception]
public class UnsupportedMessageTypeException extends DomainException {
    public UnsupportedMessageTypeException(MessageType messageType) {
        super("Brak obsługi dla typu wiadomości: " + messageType);
    }
}
