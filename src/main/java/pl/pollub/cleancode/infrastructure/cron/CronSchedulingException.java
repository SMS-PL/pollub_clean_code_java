package pl.pollub.cleancode.infrastructure.cron;

import pl.pollub.cleancode.common.DomainException;

//[Exception]
public class CronSchedulingException extends DomainException {
    public CronSchedulingException(String reason) {
        super("Nie udało się zaplanować zadania: " + reason);
    }
}
