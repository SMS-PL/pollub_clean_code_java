package pl.pollub.cleancode.greeting;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/greetings")
public class GreetingController {

    @GetMapping
    public Map<String, String> greet() {
        return Map.of("message", "API działa!");
    }

    @PostMapping
    public Map<String, String> greetByName(@Valid @RequestBody GreetingRequestDto request) {
        return Map.of("message", "Cześć, " + request.getName().trim() + "!");
    }
}
