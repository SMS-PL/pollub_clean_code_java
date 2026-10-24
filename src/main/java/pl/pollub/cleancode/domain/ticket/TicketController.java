package pl.pollub.cleancode.domain.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pollub.cleancode.common.DomainException;

import java.util.function.Consumer;

//[State] //[ISP]
@Controller
@RequiredArgsConstructor
public class TicketController {
    private final TicketReader ticketReader;
    private final TicketStateChanger ticketStateChanger;

    @GetMapping("/tickets")
    public String tickets(Model model) {
        model.addAttribute("tickets", ticketReader.findAllNewestFirst());
        return "tickets";
    }

    @PostMapping("/tickets/{ticketId}/next")
    public String moveToNextState(@PathVariable Long ticketId, RedirectAttributes redirectAttributes) {
        return changeState(ticketStateChanger::moveToNextState, ticketId, redirectAttributes);
    }

    @PostMapping("/tickets/{ticketId}/close")
    public String close(@PathVariable Long ticketId, RedirectAttributes redirectAttributes) {
        return changeState(ticketStateChanger::close, ticketId, redirectAttributes);
    }

    @PostMapping("/tickets/{ticketId}/reopen")
    public String reopen(@PathVariable Long ticketId, RedirectAttributes redirectAttributes) {
        return changeState(ticketStateChanger::reopen, ticketId, redirectAttributes);
    }

    private String changeState(Consumer<Long> stateChange, Long ticketId, RedirectAttributes redirectAttributes) {
        try {
            stateChange.accept(ticketId);
        } catch (DomainException exception) {
            redirectAttributes.addFlashAttribute("error", exception.getMessage());
        }
        return "redirect:/tickets";
    }
}
