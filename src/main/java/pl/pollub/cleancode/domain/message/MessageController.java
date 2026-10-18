package pl.pollub.cleancode.domain.message;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.infrastructure.cron.CronRepository;
import pl.pollub.cleancode.domain.message.service.MessageService;

@Controller
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final CronRepository cronRepository;

    @GetMapping("/")
    public String index(Model model) {
        MessageRequestDto request = new MessageRequestDto();
        var supportedTypes = messageService.getSupportedTypes();
        if (!supportedTypes.isEmpty()) {
            request.setType(supportedTypes.getFirst());
        }
        model.addAttribute("messageRequest", request);
        model.addAttribute("messageTypes", MessageType.values());
        model.addAttribute("supportedTypes", supportedTypes);
        model.addAttribute("crons", cronRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "index";
    }

    @PostMapping("/messages")
    public String send(@ModelAttribute MessageRequestDto request) {
        messageService.saveMessage(request);
        return "redirect:/";
    }
}
