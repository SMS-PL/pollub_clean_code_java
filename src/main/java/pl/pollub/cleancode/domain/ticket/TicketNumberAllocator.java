package pl.pollub.cleancode.domain.ticket;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TicketNumberAllocator {
    public static String allocateTicketNumber(){
        return "TICKET-"+System.currentTimeMillis();
    }
}
