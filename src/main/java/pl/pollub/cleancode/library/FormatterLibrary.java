package pl.pollub.cleancode.library;

import java.util.ArrayList;
import java.util.List;
/**
 * IMITACJA BIBLIOTEKI ZEWNĘTRZNEJ (na potrzeby wzorca ADAPTER).
 *
 * Po co ta klasa?
 * Symuluje gotową bibliotekę do formatowania tekstu, której kodu NIE możemy
 * zmieniać (tak jak nie zmieniamy np. biblioteki z Mavena). Celowo ma
 * "niewygodne" API, żeby pokazać problem, który rozwiązuje Adapter:
 *
 *  1. Niezgodny typ wejścia: metoda przyjmuje String[] (jedno słowo = jeden
 *     element), a nasza aplikacja operuje na jednym długim Stringu.
 *  2. Zbyt wiele parametrów (12): aplikacja potrzebuje tylko trzech
 *     (tekst, maxLineLength, podpis), a resztę chce ustawiać domyślnie.
 *
 * Use case: wysyłamy ładnie sformatowanego maila (łamanie linii co N znaków,
 * wielkie litery po kropce, poprawna interpunkcja na końcu).
 *
 * Rozwiązanie: FormatterLibraryAdapter implementuje nasz własny, prosty
 * interfejs TextFormatter, konwertuje String -> String[] i ukrywa zbędne
 * parametry. Reszta aplikacji (np. EmailService) zależy tylko od
 * TextFormatter, więc bibliotekę można wymienić bez zmian w logice biznesowej.
 *
 * Wzorzec: ADAPTER (Structural). Adaptee = ta klasa, Target = TextFormatter,
 * Adapter = FormatterLibraryAdapter.
 */
//[Adapter]
public class FormatterLibrary {

    /**
     * @param words               tablica slow (kazdy element = jedno slowo)
     * @param maxLineLength       maksymalna dlugosc linii (razem z wcieciem)
     * @param capitalizeSentences wielka litera na poczatku zdan
     * @param addFinalPunctuation czy dodac znak konca zdania na koncu tekstu
     * @param finalPunctuation    jaki znak dodac (np. '.')
     * @param lineSeparator       separator linii (np. "\n" lub "\r\n")
     * @param indent              wciecie dodawane na poczatku kazdej linii
     * @param trimWords           czy przycinac biale znaki w slowach
     * @param skipBlankWords      czy pomijac puste slowa i nulle
     * @param maxLines            maksymalna liczba linii (0 = bez limitu)
     * @param greeting            powitanie przed trescia (null = brak)
     * @param signature           podpis po tresci (null = brak)
     */
    //[MethodLength]
    public String formatWords(String[] words,
                              int maxLineLength,
                              boolean capitalizeSentences,
                              boolean addFinalPunctuation,
                              char finalPunctuation,
                              String lineSeparator,
                              String indent,
                              boolean trimWords,
                              boolean skipBlankWords,
                              int maxLines,
                              String greeting,
                              String signature) {
        // publiczne API "biblioteki" bez zmian, w środku metoda podzielona na kroki (max 20 linii)
        List<String> cleaned = cleanWords(words, capitalizeSentences, trimWords, skipBlankWords);
        if (addFinalPunctuation) {
            appendFinalPunctuation(cleaned, finalPunctuation);
        }
        List<String> lines = breakLines(cleaned, maxLineLength - indent.length(), maxLines);
        return joinParts(lines, lineSeparator, indent, greeting, signature);
    }

    // 1. czyszczenie slow + wielkie litery po kropce
    private List<String> cleanWords(String[] words, boolean capitalizeSentences, boolean trimWords, boolean skipBlankWords) {
        List<String> cleaned = new ArrayList<>();
        boolean capitalizeNext = true;
        for (String raw : words) {
            if (raw == null || (skipBlankWords && raw.isBlank())) {
                continue;
            }
            String word = trimWords ? raw.trim() : raw;
            if (word.isEmpty()) {
                continue;
            }
            if (capitalizeSentences && capitalizeNext) {
                word = Character.toUpperCase(word.charAt(0)) + word.substring(1);
            }
            capitalizeNext = endsSentence(word);
            cleaned.add(word);
        }
        return cleaned;
    }

    // 2. znak konca zdania na koncu tekstu
    private void appendFinalPunctuation(List<String> cleaned, char finalPunctuation) {
        if (cleaned.isEmpty()) {
            return;
        }
        int last = cleaned.size() - 1;
        if (!endsSentence(cleaned.get(last))) {
            cleaned.set(last, cleaned.get(last) + finalPunctuation);
        }
    }

    // 3. lamanie linii
    private List<String> breakLines(List<String> cleaned, int width, int maxLines) {
        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (String word : cleaned) {
            if (!line.isEmpty() && line.length() + 1 + word.length() > width) {
                lines.add(line.toString());
                line = new StringBuilder();
                if (maxLines > 0 && lines.size() == maxLines) {
                    return lines;
                }
            }
            line.append(line.isEmpty() ? "" : " ").append(word);
        }
        if (!line.isEmpty()) {
            lines.add(line.toString());
        }
        return lines;
    }

    // 4. skladanie calosci
    private String joinParts(List<String> lines, String lineSeparator, String indent, String greeting, String signature) {
        StringBuilder result = new StringBuilder();
        if (greeting != null) {
            result.append(greeting).append(lineSeparator).append(lineSeparator);
        }
        for (int i = 0; i < lines.size(); i++) {
            if (i > 0) {
                result.append(lineSeparator);
            }
            result.append(indent).append(lines.get(i));
        }
        if (signature != null) {
            result.append(lineSeparator).append(lineSeparator).append(signature);
        }
        return result.toString();
    }

    private boolean endsSentence(String text) {
        char last = text.charAt(text.length() - 1);
        return last == '.' || last == '!' || last == '?';
    }
}