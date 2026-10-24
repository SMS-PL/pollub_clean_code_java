package pl.pollub.cleancode.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pollub.cleancode.common.RequestHistoryReader;
import pl.pollub.cleancode.domain.message.entity.Message;
import pl.pollub.cleancode.domain.message.repository.MessageRepository;
import pl.pollub.cleancode.domain.ticket.TicketEntity;
import pl.pollub.cleancode.domain.ticket.TicketReader;
import pl.pollub.cleancode.infrastructure.cron.CronEntity;
import pl.pollub.cleancode.infrastructure.cron.CronRepository;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

//[Stream]
@Service
@RequiredArgsConstructor
public class StatisticsService {
    //[MagicNumber]
    private static final int TOP_PATHS_LIMIT = 5;
    private static final String MISSING_VALUE = "BRAK";

    private final MessageRepository messageRepository;
    private final CronRepository cronRepository;
    private final TicketReader ticketReader;

    public Map<String, Long> countMessagesByType() {
        return messageRepository.findAll().stream()
                .map(Message::getMessageType)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Enum::name, TreeMap::new, Collectors.counting()));
    }

    public Map<String, Long> countCronTasksByStatus() {
        return cronRepository.findAll().stream()
                .map(CronEntity::getStatus)
                .map(status -> status == null ? MISSING_VALUE : status.name())
                .collect(Collectors.groupingBy(status -> status, TreeMap::new, Collectors.counting()));
    }

    public Map<String, Long> countTicketsByPriority() {
        return ticketReader.findAllNewestFirst().stream()
                .map(TicketEntity::getPriority)
                .map(priority -> priority == null ? MISSING_VALUE : priority.name())
                .collect(Collectors.groupingBy(priority -> priority, TreeMap::new, Collectors.counting()));
    }

    public Map<String, Long> countTicketsByState() {
        return ticketReader.findAllNewestFirst().stream()
                .map(ticket -> ticket.getState().getLabel())
                .collect(Collectors.groupingBy(label -> label, TreeMap::new, Collectors.counting()));
    }

    public Map<String, Long> findMostVisitedPaths(RequestHistoryReader requestHistory) {
        return requestHistory.getEntries().stream()
                .collect(Collectors.groupingBy(entry -> entry.httpMethod() + " " + entry.path(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder()))
                .limit(TOP_PATHS_LIMIT)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (first, second) -> first, LinkedHashMap::new));
    }
}
