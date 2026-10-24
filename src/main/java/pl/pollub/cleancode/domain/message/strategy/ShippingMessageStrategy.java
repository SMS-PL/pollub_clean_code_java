package pl.pollub.cleancode.domain.message.strategy;

import org.springframework.stereotype.Component;
import pl.pollub.cleancode.domain.email.EmailAddressConstants;
import pl.pollub.cleancode.domain.email.EmailContentConstants;
import pl.pollub.cleancode.domain.inquiry.InquiryFacade;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.domain.message.observer.MessageEventPublisher;
import pl.pollub.cleancode.domain.message.repository.MessageRepository;

//[Strategy] //[DIP]
@Component
public class ShippingMessageStrategy extends AbstractMessageStrategy {
    private final InquiryFacade inquiryFacade;

    public ShippingMessageStrategy(MessageRepository messageRepository,
                                   MessageEventPublisher messageEventPublisher,
                                   InquiryFacade inquiryFacade) {
        super(messageRepository, messageEventPublisher);
        this.inquiryFacade = inquiryFacade;
    }

    @Override
    protected void createInquiry(MessageRequestDto messageRequestDto) {
        //[Facade]
        inquiryFacade.createInquiry(messageRequestDto,
                EmailAddressConstants.SHIPPING_DEPARTMENT,
                EmailContentConstants.SHIPPING_ISSUE_CONTENT);
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.SHIPPING;
    }
}
