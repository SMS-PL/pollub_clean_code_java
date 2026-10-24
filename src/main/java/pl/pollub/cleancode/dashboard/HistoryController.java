package pl.pollub.cleancode.dashboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pollub.cleancode.common.RequestHistory;
import pl.pollub.cleancode.common.RequestHistoryReader;
import pl.pollub.cleancode.common.RequestHistoryRecorder;
import pl.pollub.cleancode.domain.message.observer.MessageStatisticsObserver;

@Controller
@RequiredArgsConstructor
public class HistoryController {
    private final StatisticsService statisticsService;
    private final MessageStatisticsObserver messageStatisticsObserver;

    @GetMapping("/history")
    public String history(Model model) {
        //[Singleton] //[Iterator]
        RequestHistoryReader requestHistory = RequestHistory.getInstance();
        model.addAttribute("history", requestHistory);
        model.addAttribute("historySize", requestHistory.size());
        model.addAttribute("savedMessagesByType", messageStatisticsObserver.getSavedMessagesByType());
        model.addAttribute("messagesByType", statisticsService.countMessagesByType());
        model.addAttribute("cronTasksByStatus", statisticsService.countCronTasksByStatus());
        model.addAttribute("ticketsByPriority", statisticsService.countTicketsByPriority());
        model.addAttribute("ticketsByState", statisticsService.countTicketsByState());
        model.addAttribute("mostVisitedPaths", statisticsService.findMostVisitedPaths(requestHistory));
        return "history";
    }

    @PostMapping("/history/clear")
    public String clear() {
        //[Singleton] //[ISP]
        RequestHistoryRecorder requestHistory = RequestHistory.getInstance();
        requestHistory.clear();
        return "redirect:/history";
    }
}
