package pl.pollub.cleancode.infrastructure.adapter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

//[UnitTest]
class FormatterLibraryAdapterTest {
    private static final int LINE_LENGTH = 20;

    private final TextFormatter adapter = new FormatterLibraryAdapter();

    @Test
    void capitalizesSentencesAndAddsFinalDot() {
        String result = adapter.format("ala ma kota. kot ma ale", LINE_LENGTH, null);

        assertThat(result).isEqualTo("Ala ma kota. Kot ma\nale.");
    }

    @Test
    void breaksLinesAtGivenLength() {
        String result = adapter.format("jeden dwa trzy cztery piec szesc siedem osiem", LINE_LENGTH, null);

        assertThat(Arrays.stream(result.split("\n"))).allMatch(line -> line.length() <= LINE_LENGTH);
    }

    @Test
    void appendsSignatureAfterEmptyLine() {
        String result = adapter.format("tresc", LINE_LENGTH, "Podpis");

        assertThat(result).isEqualTo("Tresc.\n\nPodpis");
    }

    @Test
    void blankTextReturnsOnlySignature() {
        assertThat(adapter.format("   ", LINE_LENGTH, "Podpis")).isEqualTo("Podpis");
        assertThat(adapter.format(null, LINE_LENGTH, null)).isEmpty();
    }

    @Test
    void doesNotDoubleExistingPunctuationAndCollapsesSpaces() {
        String result = adapter.format("  czy   to   działa?  ", LINE_LENGTH, null);

        assertThat(result).isEqualTo("Czy to działa?");
    }
}
