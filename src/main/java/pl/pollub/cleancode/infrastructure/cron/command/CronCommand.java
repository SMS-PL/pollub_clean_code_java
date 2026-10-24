package pl.pollub.cleancode.infrastructure.cron.command;

import pl.pollub.cleancode.infrastructure.cron.CronEntity;
import pl.pollub.cleancode.infrastructure.cron.CronTypeAction;

//[Command] //[DIP]
public interface CronCommand {
    CronTypeAction getSupportedType();

    void execute(CronEntity cronEntity);
}
