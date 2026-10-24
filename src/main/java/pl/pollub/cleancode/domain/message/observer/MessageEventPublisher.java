package pl.pollub.cleancode.domain.message.observer;

import org.springframework.stereotype.Component;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

//[Observer]
@Component
public class MessageEventPublisher {
    private final List<MessageSavedObserver> observers = new CopyOnWriteArrayList<>();

    public MessageEventPublisher(List<MessageSavedObserver> initialObservers) {
        initialObservers.forEach(this::subscribe);
    }

    public void subscribe(MessageSavedObserver observer) {
        observers.add(observer);
    }

    public void unsubscribe(MessageSavedObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(MessageRequestDto request) {
        observers.forEach(observer -> observer.onMessageSaved(request));
    }

    public List<MessageSavedObserver> getObservers() {
        return List.copyOf(observers);
    }
}
