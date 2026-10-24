package pl.pollub.cleancode.domain.message.strategy;

import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.mapper.MessageMapper;
import pl.pollub.cleancode.domain.message.observer.MessageEventPublisher;
import pl.pollub.cleancode.domain.message.repository.MessageRepository;
import pl.pollub.cleancode.domain.message.validation.ContentTransformers;

//[TemplateMethod] //[DIP]
public abstract class AbstractMessageStrategy implements MessageStrategy {
    private final MessageRepository messageRepository;
    private final MessageEventPublisher messageEventPublisher;

    protected AbstractMessageStrategy(MessageRepository messageRepository, MessageEventPublisher messageEventPublisher) {
        this.messageRepository = messageRepository;
        this.messageEventPublisher = messageEventPublisher;
    }

    //[TemplateMethod]
    @Override
    public final void saveMessage(MessageRequestDto messageRequestDto) {
        //[FunctionalInterface]
        messageRequestDto.setMessage(ContentTransformers.NORMALIZE.transform(messageRequestDto.getMessage()));
        createInquiry(messageRequestDto);
        messageRepository.save(MessageMapper.mapToEntity(messageRequestDto));
        //[Observer]
        messageEventPublisher.notifyObservers(messageRequestDto);
    }

    //[TemplateMethod]
    protected abstract void createInquiry(MessageRequestDto messageRequestDto);
}
