package pl.pollub.cleancode.infrastructure.cron;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pollub.cleancode.infrastructure.cron.command.CronCommand;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//[Command] //[ISP] //[DIP]
@Service
@RequiredArgsConstructor
public class CronScheduler implements CronTaskQueue, CronTaskRunner {
    //[MagicNumber]
    private static final long EXECUTION_DELAY_MS = 60_000; // co minutę

    private final CronRepository cronRepository;
    private final List<CronCommand> cronCommands;

    @Override
    public CronEntity addNewScheduledTask(CronTypeAction cronTypeAction,
                                          String emailAddress,
                                          String content) {
        if (emailAddress == null || emailAddress.isBlank()) {
            //[Exception]
            throw new CronSchedulingException("brak adresu e-mail odbiorcy");
        }
        return cronRepository.save(
                CronEntity.builder()
                        .cronType(cronTypeAction)
                        .status(CronStatus.QUEUED)
                        .emailAddress(emailAddress)
                        .content(content)
                        .scheduledAt(System.currentTimeMillis())
                        .build()
        );
    }

    @Override
    @Scheduled(fixedDelay = EXECUTION_DELAY_MS)
    @Transactional
    public int executeScheduledTasks() {
        Map<CronTypeAction, CronCommand> commandsByType = cronCommands.stream()
                .collect(Collectors.toMap(CronCommand::getSupportedType, Function.identity()));
        List<CronEntity> queuedTasks = cronRepository.findByExecutedAtIsNullAndStatus(CronStatus.QUEUED);
        for (CronEntity cronEntity : queuedTasks) {
            //[Command]
            commandsByType.get(cronEntity.getCronType()).execute(cronEntity);
            cronRepository.save(cronEntity);
        }
        return queuedTasks.size();
    }
}
