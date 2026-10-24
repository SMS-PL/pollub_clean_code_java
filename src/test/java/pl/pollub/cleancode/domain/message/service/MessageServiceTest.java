package pl.pollub.cleancode.domain.message.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.domain.message.exception.InvalidMessageException;
import pl.pollub.cleancode.domain.message.exception.UnsupportedMessageTypeException;
import pl.pollub.cleancode.domain.message.strategy.MessageStrategy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//[UnitTest]
class MessageServiceTest {
    private final MessageStrategy paymentStrategy = mock(MessageStrategy.class);
    private final MessageStrategy shippingStrategy = mock(MessageStrategy.class);
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        when(paymentStrategy.getMessageType()).thenReturn(MessageType.PAYMENT);
        when(shippingStrategy.getMessageType()).thenReturn(MessageType.SHIPPING);
        messageService = new MessageService(List.of(paymentStrategy, shippingStrategy));
        messageService.init();
    }

    @Test
    void supportedTypesContainOnlyTypesWithStrategy() {
        assertThat(messageService.getSupportedTypes())
                .containsExactly(MessageType.SHIPPING, MessageType.PAYMENT)
                .doesNotContain(MessageType.RETURN);
    }

    @Test
    void saveMessageDelegatesToStrategyForGivenType() {
        MessageRequestDto request = request(MessageType.SHIPPING, "Paczka nie dotarła");

        messageService.saveMessage(request);

        verify(shippingStrategy).apply(request);
        verify(paymentStrategy, never()).apply(any());
    }

    @Test
    void saveMessageThrowsForTypeWithoutStrategy() {
        assertThatThrownBy(() -> messageService.saveMessage(request(MessageType.RETURN, "Chcę zwrócić towar")))
                .isInstanceOf(UnsupportedMessageTypeException.class)
                .hasMessageContaining("RETURN");
    }

    @Test
    void saveMessageRejectsBlankContent() {
        assertThatThrownBy(() -> messageService.saveMessage(request(MessageType.PAYMENT, "   ")))
                .isInstanceOf(InvalidMessageException.class)
                .hasMessageContaining("pusta");
        verify(paymentStrategy, never()).apply(any());
    }

    @Test
    void saveMessageRejectsForbiddenWord() {
        assertThatThrownBy(() -> messageService.saveMessage(request(MessageType.PAYMENT, "Tania REKLAMA tutaj")))
                .isInstanceOf(InvalidMessageException.class)
                .hasMessageContaining("reklama");
    }

    private MessageRequestDto request(MessageType type, String message) {
        return MessageRequestDto.builder().type(type).message(message).build();
    }
}
