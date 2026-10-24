package pl.pollub.cleancode.domain.message.validation;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

//[Lambda]
@UtilityClass
public class MessageContentValidators {
    //[MagicNumber]
    public static final int MAX_MESSAGE_LENGTH = 500;
    public static final int MIN_MESSAGE_LENGTH = 3;
    public static final List<String> FORBIDDEN_WORDS = List.of("spam", "reklama", "kasyno");

    public static MessageContentValidator notBlank() {
        return content -> content == null || content.isBlank()
                ? Optional.of("treść nie może być pusta") : Optional.empty();
    }

    public static MessageContentValidator lengthBetween(int minLength, int maxLength) {
        return content -> {
            int length = content.trim().length();
            if (length < minLength || length > maxLength) {
                return Optional.of("długość musi być od " + minLength + " do " + maxLength + " znaków");
            }
            return Optional.empty();
        };
    }

    public static List<MessageContentValidator> defaultValidators() {
        return List.of(
                notBlank(),
                lengthBetween(MIN_MESSAGE_LENGTH, MAX_MESSAGE_LENGTH),
                new ForbiddenWordsValidator(FORBIDDEN_WORDS)
        );
    }
}
