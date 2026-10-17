package pl.pollub.cleancode.domain.message.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pollub.cleancode.domain.inquiry.InquiryEntity;
import pl.pollub.cleancode.domain.inquiry.InquiryFacade;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.domain.message.mapper.MessageMapper;
import pl.pollub.cleancode.domain.message.repository.MessageRepository;

@Component
@RequiredArgsConstructor
public class PaymentMessageStrategy implements MessageStrategy{
    private final MessageRepository messageRepository;
    private final InquiryFacade inquiryFacade;


    @Override
    public void saveMessage(MessageRequestDto messageRequestDto) {
        inquiryFacade.createInquryForPaymentDepartment(messageRequestDto);
        messageRepository.save(MessageMapper.mapToEntity(messageRequestDto));
    }

    @Override
    public MessageType getMessageType() {
        return MessageType.PAYMENT;
    }
}
