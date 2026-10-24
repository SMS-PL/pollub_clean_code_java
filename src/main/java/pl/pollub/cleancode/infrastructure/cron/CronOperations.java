package pl.pollub.cleancode.infrastructure.cron;

import java.util.List;

//[ISP]
@Deprecated
public interface CronOperations {
    CronEntity addNewScheduledTask(CronTypeAction cronTypeAction, String emailAddress, String content);

    int executeScheduledTasks();

    void cancelTask(Long cronId);

    List<CronEntity> findAllNewestFirst();

    String generateDailyReport();
}
