package pl.pollub.cleancode.domain.message.strategy;

import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;

public interface MessageStrategy {

    void saveMessage(MessageRequestDto messageRequestDto);

    MessageType getMessageType();

    default void apply(MessageRequestDto messageRequestDto){
        try{
            saveMessage(messageRequestDto);
        }catch (Exception e){
            throw new RuntimeException("Error while saving message: " + e.getMessage());
        }
    }
}
