package pl.pollub.cleancode.infrastructure.cron;

//[ISP]
public interface CronTaskQueue {
    CronEntity addNewScheduledTask(CronTypeAction cronTypeAction, String emailAddress, String content);
}
