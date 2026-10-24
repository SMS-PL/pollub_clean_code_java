package pl.pollub.cleancode.infrastructure.cron;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Przycisk "Uruchom zadania teraz" - żeby nie czekać minuty na @Scheduled podczas prezentacji
//[Command]
@Controller
@RequiredArgsConstructor
public class CronController {
    private final CronTaskRunner cronTaskRunner;

    @PostMapping("/crons/run")
    public String runNow(RedirectAttributes redirectAttributes) {
        int executedTasks = cronTaskRunner.executeScheduledTasks();
        redirectAttributes.addFlashAttribute("success", "Wykonano zadań: " + executedTasks);
        return "redirect:/";
    }
}
