package pl.pollub.cleancode.domain.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.service.MessageService;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("messageRequest", new MessageRequestDto());
        return "index";
    }

    @PostMapping("/messages")
    public String send(@ModelAttribute MessageRequestDto request) {
        messageService.saveMessage(request);
        return "redirect:/";
    }
}
