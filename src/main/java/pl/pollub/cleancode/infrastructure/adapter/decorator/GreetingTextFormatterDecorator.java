package pl.pollub.cleancode.infrastructure.adapter.decorator;

import pl.pollub.cleancode.infrastructure.adapter.TextFormatter;

//[Decorator]
public class GreetingTextFormatterDecorator extends TextFormatterDecorator {
    private static final String GREETING = "Dzień dobry,";

    public GreetingTextFormatterDecorator(TextFormatter wrappedFormatter) {
        super(wrappedFormatter);
    }

    @Override
    public String format(String text, int maxLineLength, String signature) {
        return GREETING + "\n\n" + super.format(text, maxLineLength, signature);
    }
}
