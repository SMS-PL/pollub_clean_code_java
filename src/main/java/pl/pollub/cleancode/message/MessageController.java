package pl.pollub.cleancode.message;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pollub.cleancode.message.dto.MessageRequestDto;

@Controller
public class MessageController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("messageRequest", new MessageRequestDto());
        return "index";
    }

    @PostMapping("/messages")
    public String send(@ModelAttribute MessageRequestDto request) {
        System.out.println(request.getMessage());
        return "redirect:/";
    }
}
