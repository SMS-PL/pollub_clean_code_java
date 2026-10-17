package pl.pollub.cleancode.domain.inquiry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pollub.cleancode.domain.email.EmailAddressConstants;
import pl.pollub.cleancode.domain.email.EmailContentConstants;
import pl.pollub.cleancode.domain.email.EmailService;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.ticket.PriorityEnum;
import pl.pollub.cleancode.domain.ticket.TicketEntity;
import pl.pollub.cleancode.domain.ticket.TicketRepository;
import pl.pollub.cleancode.infrastructure.cron.CronScheduler;
import pl.pollub.cleancode.infrastructure.cron.CronStatus;
import pl.pollub.cleancode.infrastructure.cron.CronTypeAction;

@Service
@RequiredArgsConstructor
public class InquiryFacade {
    private final InquiryRepository inquiryRepository;
    private final TicketRepository ticketRepository;
    private final CronScheduler cronScheduler;
    private final EmailService emailService;

    public InquiryEntity createInquryForPaymentDepartment(MessageRequestDto messageRequestDto) {
        var ticketEntity = TicketEntity.builder()
                .content(messageRequestDto.getMessage())
                .priority(PriorityEnum.HIGH)
                .build();
        var savedEntity = ticketRepository.save(ticketEntity);

        CronStatus cronStatus = cronScheduler.addNewScheduledTask(
                CronTypeAction.SEND_EMAIL,
                EmailAddressConstants.PAYMENT_DEPARTMENT,
                EmailContentConstants.PAYMENT_ISSUE_CONTENT
        );

        var inquiryEntity = InquiryEntity.builder()
                .content(messageRequestDto.getMessage())
                .email(EmailAddressConstants.PAYMENT_DEPARTMENT)
                .content(EmailContentConstants.PAYMENT_ISSUE_CONTENT)
                .ticketId(savedEntity.getId())
                .status(cronStatus == CronStatus.QUEUED ? InquiryStatus.QUEUED : InquiryStatus.ERROR)
                .build();
        return inquiryRepository.save(inquiryEntity);
    }
}
