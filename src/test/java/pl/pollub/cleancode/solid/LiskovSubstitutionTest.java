package pl.pollub.cleancode.solid;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pl.pollub.cleancode.domain.email.CountingEmailService;
import pl.pollub.cleancode.domain.email.EmailService;
import pl.pollub.cleancode.domain.message.dto.MessageRequestDto;
import pl.pollub.cleancode.domain.message.enums.MessageType;
import pl.pollub.cleancode.domain.ticket.*;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

//[LSP]
class LiskovSubstitutionTest {

    static Stream<TicketNumberGenerator> generators() {
        return Stream.of(new TicketNumberGenerator(), new DatedTicketNumberGenerator());
    }

    static Stream<TicketPriorityResolver> resolvers() {
        return Stream.of(new TicketPriorityResolver(), new KeywordTicketPriorityResolver());
    }

    static Stream<EmailService> emailServices() {
        return Stream.of(new EmailService(), new CountingEmailService());
    }

    @ParameterizedTest
    @MethodSource("generators")
    void everyGeneratorReturnsNumberWithTicketPrefix(TicketNumberGenerator generator) {
        assertThat(generator.generate()).startsWith("TICKET-").hasSizeGreaterThan("TICKET-".length());
    }

    @ParameterizedTest
    @MethodSource("resolvers")
    void everyResolverReturnsPriorityForEachType(TicketPriorityResolver resolver) {
        for (MessageType type : MessageType.values()) {
            MessageRequestDto request = MessageRequestDto.builder().type(type).message("zwykła wiadomość").build();
            MessageRequestDto requestWithoutMessage = MessageRequestDto.builder().type(type).build();
            assertThat(resolver.resolve(request)).isNotNull();
            assertThat(resolver.resolve(requestWithoutMessage)).isNotNull();
        }
    }

    @ParameterizedTest
    @MethodSource("emailServices")
    void everyEmailServiceSendsWithoutError(EmailService emailService) {
        assertThatCode(() -> emailService.sendEmail("test@pollub.pl", "Temat", "Treść")).doesNotThrowAnyException();
    }
}
