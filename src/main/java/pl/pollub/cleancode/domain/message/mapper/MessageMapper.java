package pl.pollub.cleancode.domain.message.mapper;

import lombok.experimental.UtilityClass;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.entity.Message;

@UtilityClass
public class MessageMapper {

    public static Message mapToEntity(MessageRequestDto messageRequestDto){
        return Message.builder()
                .message(messageRequestDto.getMessage())
                .messageType(messageRequestDto.getType())
                .build();
    }

    public static MessageRequestDto mapToDto(Message message){
        return MessageRequestDto.builder()
                .message(message.getMessage())
                .type(message.getMessageType())
                .build();
    }
}
