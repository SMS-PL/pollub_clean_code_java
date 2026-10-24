package pl.pollub.cleancode.infrastructure.cron.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pollub.cleancode.domain.email.EmailContentConstants;
import pl.pollub.cleancode.domain.email.EmailService;
import pl.pollub.cleancode.infrastructure.cron.CronEntity;
import pl.pollub.cleancode.infrastructure.cron.CronTypeAction;

//[Command] //[DIP]
@Component
@RequiredArgsConstructor
public class SendEmailCommand extends AbstractCronCommand {
    private final EmailService emailService;

    @Override
    public CronTypeAction getSupportedType() {
        return CronTypeAction.SEND_EMAIL;
    }

    @Override
    protected void run(CronEntity cronEntity) {
        emailService.sendEmail(
                cronEntity.getEmailAddress(),
                EmailContentConstants.SCHEDULED_EMAIL_SUBJECT,
                cronEntity.getContent()
        );
    }
}
