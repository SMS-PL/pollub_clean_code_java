package pl.pollub.cleancode.domain.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;

import java.util.List;
import java.util.function.Consumer;

//[ISP]
@Service
@RequiredArgsConstructor
public class TicketService implements TicketCreator, TicketReader, TicketStateChanger {
    private final TicketRepository ticketRepository;
    private final TicketFactory ticketFactory;

    @Override
    public TicketEntity createTicket(MessageRequestDto request) {
        //[Factory]
        return ticketRepository.save(ticketFactory.createTicket(request));
    }

    @Override
    public List<TicketEntity> findAllNewestFirst() {
        return ticketRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    @Override
    public TicketEntity findById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(ticketId));
    }

    @Override
    @Transactional
    public void moveToNextState(Long ticketId) {
        changeState(ticketId, TicketEntity::moveToNextState);
    }

    @Override
    @Transactional
    public void close(Long ticketId) {
        changeState(ticketId, TicketEntity::close);
    }

    @Override
    @Transactional
    public void reopen(Long ticketId) {
        changeState(ticketId, TicketEntity::reopen);
    }

    private void changeState(Long ticketId, Consumer<TicketEntity> stateChange) {
        TicketEntity ticket = findById(ticketId);
        stateChange.accept(ticket);
        ticketRepository.save(ticket);
    }
}
