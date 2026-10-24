package pl.pollub.cleancode.domain.message.observer;

import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;

//[Observer]
public interface MessageSavedObserver {
    void onMessageSaved(MessageRequestDto request);
}
