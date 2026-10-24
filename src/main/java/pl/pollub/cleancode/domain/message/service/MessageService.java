package pl.pollub.cleancode.domain.message.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.domain.message.exception.InvalidMessageException;
import pl.pollub.cleancode.domain.message.exception.UnsupportedMessageTypeException;
import pl.pollub.cleancode.domain.message.strategy.MessageStrategy;
import pl.pollub.cleancode.domain.message.validation.MessageContentValidator;
import pl.pollub.cleancode.domain.message.validation.MessageContentValidators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

//[Strategy] //[DIP]
@Service
@RequiredArgsConstructor
public class MessageService {
    private final List<MessageStrategy> messageStrategies;
    private final List<MessageContentValidator> contentValidators = MessageContentValidators.defaultValidators();
    private Map<MessageType, MessageStrategy> messageStrategyMap;

    @PostConstruct
    public void init(){
        this.messageStrategyMap = messageStrategies.stream().collect(
                Collectors.toMap(
                       MessageStrategy::getMessageType,
                        Function.identity()
                )
        );
    }

    public List<MessageType> getSupportedTypes() {
        return Arrays.stream(MessageType.values())
                .filter(messageStrategyMap::containsKey)
                .toList();
    }

    @Transactional
    public void saveMessage(MessageRequestDto request) {
        validateContent(request.getMessage());
        //[Strategy]
        MessageStrategy messageStrategy = messageStrategyMap.get(request.getType());
        if(messageStrategy == null){
            //[Exception]
            throw new UnsupportedMessageTypeException(request.getType());
        }
        messageStrategy.apply(request);
    }

    //[Lambda] //[Stream]
    private void validateContent(String content) {
        contentValidators.stream()
                .map(validator -> validator.validate(content))
                .flatMap(Optional::stream)
                .findFirst()
                .ifPresent(error -> {
                    //[Exception]
                    throw new InvalidMessageException(error);
                });
    }
}
