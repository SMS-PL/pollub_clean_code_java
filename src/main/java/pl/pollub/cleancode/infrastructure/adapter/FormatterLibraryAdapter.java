package pl.pollub.cleancode.infrastructure.adapter;

import org.springframework.stereotype.Component;
import pl.pollub.cleancode.library.FormatterLibrary;

//[Adapter] //[DIP]
@Component
public class FormatterLibraryAdapter extends AbstractTextFormatter {
    //[MagicNumber]
    private static final char FINAL_PUNCTUATION = '.';
    private static final String LINE_SEPARATOR = "\n";
    private static final String NO_INDENT = "";
    private static final int UNLIMITED_LINES = 0;

    private final FormatterLibrary library = new FormatterLibrary();

    @Override
    protected String formatText(String text, int maxLineLength, String signature) {
        String[] words = text.trim().split("\\s+");   // String -> String[]
        //[Adapter]
        return library.formatWords(
                words,
                maxLineLength,
                true,      // capitalizeSentences
                true,      // addFinalPunctuation
                FINAL_PUNCTUATION,
                LINE_SEPARATOR,
                NO_INDENT,
                true,      // trimWords
                true,      // skipBlankWords
                UNLIMITED_LINES,
                null,      // greeting
                signature
        );
    }
}
