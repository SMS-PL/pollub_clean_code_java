package pl.pollub.cleancode.domain.inquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.ticket.TicketCreator;
import pl.pollub.cleancode.domain.ticket.TicketEntity;
import pl.pollub.cleancode.infrastructure.adapter.TextFormatter;
import pl.pollub.cleancode.infrastructure.cron.CronTaskQueue;
import pl.pollub.cleancode.infrastructure.cron.CronTypeAction;

//[Facade] //[DIP]
@Service
@RequiredArgsConstructor
public class InquiryFacade {
    //[MagicNumber]
    private static final String SIGNATURE = "PollubCorp2026";
    private static final int EMAIL_LINE_LENGTH = 50;

    private final InquiryRepository inquiryRepository;
    private final TicketCreator ticketCreator;
    private final CronTaskQueue cronTaskQueue;
    private final TextFormatter textFormatter;

    //[Facade] //[Naming]
    public InquiryEntity createInquiry(MessageRequestDto request, String departmentEmail, String emailTemplate) {
        TicketEntity ticket = ticketCreator.createTicket(request);
        String emailContent = prepareEmailContent(ticket, emailTemplate);
        cronTaskQueue.addNewScheduledTask(CronTypeAction.SEND_EMAIL, departmentEmail, emailContent);
        return inquiryRepository.save(InquiryEntity.builder()
                .ticketId(ticket.getId())
                .emailAddress(departmentEmail)
                .content(emailContent)
                .status(InquiryStatus.QUEUED)
                .build());
    }

    private String prepareEmailContent(TicketEntity ticket, String emailTemplate) {
        String text = emailTemplate + " Ticket " + ticket.getTicketNumber() + ". Customer wrote: " + ticket.getContent();
        return textFormatter.format(text, EMAIL_LINE_LENGTH, SIGNATURE);
    }
}
