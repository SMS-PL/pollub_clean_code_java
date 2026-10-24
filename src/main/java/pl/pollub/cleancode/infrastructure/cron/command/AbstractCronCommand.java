package pl.pollub.cleancode.infrastructure.cron.command;

import pl.pollub.cleancode.infrastructure.cron.CronEntity;
import pl.pollub.cleancode.infrastructure.cron.CronStatus;

//[DIP] //[Command] //[TemplateMethod]
public abstract class AbstractCronCommand implements CronCommand {

    @Override
    public final void execute(CronEntity cronEntity) {
        try {
            run(cronEntity);
            cronEntity.setStatus(CronStatus.SENT);
        } catch (RuntimeException exception) {
            cronEntity.setStatus(CronStatus.ERROR);
        }
        cronEntity.setExecutedAt(System.currentTimeMillis());
    }

    protected abstract void run(CronEntity cronEntity);
}
