package pl.pollub.cleancode.infrastructure.adapter.decorator;

import pl.pollub.cleancode.infrastructure.adapter.TextFormatter;

//[Decorator]
public abstract class TextFormatterDecorator implements TextFormatter {
    private final TextFormatter wrappedFormatter;

    protected TextFormatterDecorator(TextFormatter wrappedFormatter) {
        this.wrappedFormatter = wrappedFormatter;
    }

    @Override
    public String format(String text, int maxLineLength, String signature) {
        return wrappedFormatter.format(text, maxLineLength, signature);
    }
}
