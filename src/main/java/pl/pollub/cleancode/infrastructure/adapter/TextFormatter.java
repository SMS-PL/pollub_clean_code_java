package pl.pollub.cleancode.infrastructure.adapter;

public interface TextFormatter {
    String format(String text, int maxLineLength, String signature);
}
