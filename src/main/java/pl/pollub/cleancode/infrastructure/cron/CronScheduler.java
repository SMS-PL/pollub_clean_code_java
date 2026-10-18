package pl.pollub.cleancode.infrastructure.cron;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pollub.cleancode.domain.email.EmailService;

@Service
@RequiredArgsConstructor
public class CronScheduler {
    private final CronRepository cronRepository;
    private final EmailService emailService;

    public CronStatus addNewScheduledTask(CronTypeAction cronTypeAction,
                                          String emailAddress,
                                          String content) {
        return cronRepository.save(
                CronEntity.builder()
                        .cronType(cronTypeAction)
                        .status(CronStatus.QUEUED)
                        .emailAddress(emailAddress)
                        .content(content)
                        .build()
        ).getStatus();
    }

    @Scheduled(fixedDelay = 60_000) // co minutę
    @Transactional
    public void executeScheduledTasks() {
        for (CronEntity cronEntity : cronRepository.findNotExecuted()) {
            CronTypeAction cronType = cronEntity.getCronType();

            switch (cronType) {
                case SEND_EMAIL:
                    emailService.sendEmail(
                            cronEntity.getEmailAddress(),
                            "Scheduled Email",
                            cronEntity.getContent()
                    );
                    cronEntity.setStatus(CronStatus.SENT);
                    cronRepository.save(cronEntity);
                    break;
                default:
                    break;
            }
        }

    }
}
