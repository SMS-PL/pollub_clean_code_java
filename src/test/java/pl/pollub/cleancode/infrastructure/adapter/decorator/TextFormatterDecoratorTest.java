package pl.pollub.cleancode.infrastructure.adapter.decorator;

import org.junit.jupiter.api.Test;
import pl.pollub.cleancode.infrastructure.adapter.TextFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class TextFormatterDecoratorTest {
    private static final int LINE_LENGTH = 50;

    private final TextFormatter plainFormatter = (text, maxLineLength, signature) -> text;

    @Test
    void greetingDecoratorAddsGreetingBeforeText() {
        TextFormatter formatter = new GreetingTextFormatterDecorator(plainFormatter);

        assertThat(formatter.format("Treść", LINE_LENGTH, null)).isEqualTo("Dzień dobry,\n\nTreść");
    }

    @Test
    void disclaimerDecoratorAddsFooterAfterText() {
        TextFormatter formatter = new DisclaimerTextFormatterDecorator(plainFormatter);

        assertThat(formatter.format("Treść", LINE_LENGTH, null))
                .startsWith("Treść\n\n--")
                .endsWith("prosimy na nią nie odpowiadać.");
    }

    @Test
    void decoratorsCanBeStackedInAnyOrder() {
        TextFormatter formatter = new GreetingTextFormatterDecorator(new DisclaimerTextFormatterDecorator(plainFormatter));

        String result = formatter.format("Treść", LINE_LENGTH, null);

        assertThat(result).startsWith("Dzień dobry,").contains("Treść").endsWith("odpowiadać.");
    }
}
