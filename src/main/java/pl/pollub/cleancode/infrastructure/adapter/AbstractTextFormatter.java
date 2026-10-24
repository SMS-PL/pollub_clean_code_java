package pl.pollub.cleancode.infrastructure.adapter;

//[DIP]
public abstract class AbstractTextFormatter implements TextFormatter {

    @Override
    public final String format(String text, int maxLineLength, String signature) {
        if (text == null || text.isBlank()) {
            return signature == null ? "" : signature;
        }
        return formatText(text, maxLineLength, signature);
    }

    protected abstract String formatText(String text, int maxLineLength, String signature);
}
