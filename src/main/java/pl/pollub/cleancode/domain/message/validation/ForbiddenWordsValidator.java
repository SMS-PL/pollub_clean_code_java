package pl.pollub.cleancode.domain.message.validation;

import java.util.List;
import java.util.Optional;

//[FunctionalInterface]
public class ForbiddenWordsValidator implements MessageContentValidator {
    private final List<String> forbiddenWords;

    public ForbiddenWordsValidator(List<String> forbiddenWords) {
        this.forbiddenWords = forbiddenWords;
    }

    @Override
    public Optional<String> validate(String content) {
        String lowerCaseContent = content.toLowerCase();
        return forbiddenWords.stream()
                .filter(lowerCaseContent::contains)
                .findFirst()
                .map(word -> "zawiera niedozwolone słowo '" + word + "'");
    }
}
