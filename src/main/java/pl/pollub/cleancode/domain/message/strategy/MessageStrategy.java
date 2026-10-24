package pl.pollub.cleancode.domain.message.strategy;

import pl.pollub.cleancode.common.DomainException;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.domain.message.exception.MessageProcessingException;

//[Strategy] //[DIP]
public interface MessageStrategy {

    void saveMessage(MessageRequestDto messageRequestDto);

    MessageType getMessageType();

    default void apply(MessageRequestDto messageRequestDto){
        try{
            saveMessage(messageRequestDto);
        }catch (DomainException e){
            throw e;
        }catch (RuntimeException e){
            throw new MessageProcessingException(e.getMessage());
        }
    }
}
