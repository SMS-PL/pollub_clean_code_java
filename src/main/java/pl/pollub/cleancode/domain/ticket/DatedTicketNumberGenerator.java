package pl.pollub.cleancode.domain.ticket;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//[LSP]
public class DatedTicketNumberGenerator extends TicketNumberGenerator {
    //[MagicNumber]
    private static final int SEQUENCE_MODULO = 100_000;

    @Override
    public String generate() {
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        long sequence = System.currentTimeMillis() % SEQUENCE_MODULO;
        return TICKET_NUMBER_PREFIX + date + "-" + sequence;
    }
}
