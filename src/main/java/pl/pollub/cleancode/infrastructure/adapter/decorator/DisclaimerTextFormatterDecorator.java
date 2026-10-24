package pl.pollub.cleancode.infrastructure.adapter.decorator;

import pl.pollub.cleancode.infrastructure.adapter.TextFormatter;

//[Decorator]
public class DisclaimerTextFormatterDecorator extends TextFormatterDecorator {
    private static final String DISCLAIMER = "-- Wiadomość wygenerowana automatycznie, prosimy na nią nie odpowiadać.";

    public DisclaimerTextFormatterDecorator(TextFormatter wrappedFormatter) {
        super(wrappedFormatter);
    }

    @Override
    public String format(String text, int maxLineLength, String signature) {
        return super.format(text, maxLineLength, signature) + "\n\n" + DISCLAIMER;
    }
}
