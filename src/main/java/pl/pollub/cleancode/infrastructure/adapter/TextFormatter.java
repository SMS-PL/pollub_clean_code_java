package pl.pollub.cleancode.infrastructure.adapter;

//[Adapter] //[DIP]
public interface TextFormatter {
    String format(String text, int maxLineLength, String signature);
}
