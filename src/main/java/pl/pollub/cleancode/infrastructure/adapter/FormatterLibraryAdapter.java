package pl.pollub.cleancode.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.pollub.cleancode.library.FormatterLibrary;

@Component
@RequiredArgsConstructor
public class FormatterLibraryAdapter implements TextFormatter {

    private final FormatterLibrary library = new FormatterLibrary();

    @Override
    public String format(String text, int maxLineLength, String signature) {
        String[] words = text.trim().split("\\s+");   // String -> String[]
        return library.formatWords(
                words,
                maxLineLength,
                true,      // capitalizeSentences
                true,      // addFinalPunctuation
                '.',       // finalPunctuation
                "\n",      // lineSeparator
                "",        // indent
                true,      // trimWords
                true,      // skipBlankWords
                0,         // maxLines (bez limitu)
                null,      // greeting
                signature
        );
    }
}
