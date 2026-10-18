package pl.pollub.cleancode.domain.message.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.domain.message.strategy.MessageStrategy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

// [STRATEGIA] użycie strategii
@Service
@RequiredArgsConstructor
public class MessageService {
    private final List<MessageStrategy> messageStrategies;
    private  Map<MessageType, MessageStrategy> messageStrategyMap;

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
        return java.util.Arrays.stream(MessageType.values())
                .filter(messageStrategyMap::containsKey)
                .toList();
    }

    public void saveMessage(MessageRequestDto request) {
        MessageStrategy messageStrategy = messageStrategyMap.get(request.getType());
        if(messageStrategy == null){
            throw new IllegalArgumentException("No strategy found for message type: " + request.getType());
        }
        messageStrategy.apply(request);
    }
}
