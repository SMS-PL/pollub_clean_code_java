package pl.pollub.cleancode.domain.message.observer;

import org.springframework.stereotype.Component;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;

import java.util.EnumMap;
import java.util.Map;

//[Observer]
@Component
public class MessageStatisticsObserver implements MessageSavedObserver {
    private final Map<MessageType, Integer> savedMessagesByType = new EnumMap<>(MessageType.class);

    @Override
    public synchronized void onMessageSaved(MessageRequestDto request) {
        savedMessagesByType.merge(request.getType(), 1, Integer::sum);
    }

    public synchronized Map<MessageType, Integer> getSavedMessagesByType() {
        return Map.copyOf(savedMessagesByType);
    }
}
