package pl.pollub.cleancode.domain.message.validation;

import java.util.Optional;

//[FunctionalInterface]
@FunctionalInterface
public interface MessageContentValidator {
    Optional<String> validate(String content);
}
