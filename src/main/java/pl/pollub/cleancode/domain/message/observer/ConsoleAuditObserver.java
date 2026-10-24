package pl.pollub.cleancode.domain.message.observer;

import org.springframework.stereotype.Component;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;

//[Observer]
@Component
public class ConsoleAuditObserver implements MessageSavedObserver {
    @Override
    public void onMessageSaved(MessageRequestDto request) {
        System.out.println("[AUDYT] Zapisano wiadomość typu " + request.getType() + ": " + request.getMessage());
    }
}
