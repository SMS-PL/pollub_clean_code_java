package pl.pollub.cleancode.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import pl.pollub.cleancode.domain.email.CountingEmailService;
import pl.pollub.cleancode.domain.email.EmailService;
import pl.pollub.cleancode.domain.ticket.DatedTicketNumberGenerator;
import pl.pollub.cleancode.domain.ticket.KeywordTicketPriorityResolver;
import pl.pollub.cleancode.domain.ticket.TicketNumberGenerator;
import pl.pollub.cleancode.domain.ticket.TicketPriorityResolver;
import pl.pollub.cleancode.infrastructure.adapter.FormatterLibraryAdapter;
import pl.pollub.cleancode.infrastructure.adapter.TextFormatter;
import pl.pollub.cleancode.infrastructure.adapter.decorator.DisclaimerTextFormatterDecorator;
import pl.pollub.cleancode.infrastructure.adapter.decorator.GreetingTextFormatterDecorator;

// Tutaj wybieramy konkretne implementacje. Reszta aplikacji zależy od typów bazowych / interfejsów.
@Configuration
public class ImplementationConfig {

    //[LSP]
    @Bean
    public EmailService emailService() {
        return new CountingEmailService();
    }

    //[LSP]
    @Bean
    public TicketNumberGenerator ticketNumberGenerator() {
        return new DatedTicketNumberGenerator();
    }

    //[LSP]
    @Bean
    public TicketPriorityResolver ticketPriorityResolver() {
        return new KeywordTicketPriorityResolver();
    }

    //[Decorator]
    @Bean
    @Primary
    public TextFormatter decoratedTextFormatter(FormatterLibraryAdapter formatterLibraryAdapter) {
        return new GreetingTextFormatterDecorator(
                new DisclaimerTextFormatterDecorator(formatterLibraryAdapter));
    }
}
