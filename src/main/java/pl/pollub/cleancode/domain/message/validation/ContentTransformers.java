package pl.pollub.cleancode.domain.message.validation;

import lombok.experimental.UtilityClass;

//[Lambda]
@UtilityClass
public class ContentTransformers {
    public static final ContentTransformer TRIM = String::trim;

    public static final ContentTransformer COLLAPSE_SPACES = content -> content.replaceAll("\\s+", " ");

    public static final ContentTransformer CAPITALIZE_FIRST_LETTER = content -> content.isEmpty()
            ? content
            : Character.toUpperCase(content.charAt(0)) + content.substring(1);

    public static final ContentTransformer NORMALIZE = TRIM.andThen(COLLAPSE_SPACES).andThen(CAPITALIZE_FIRST_LETTER);
}
