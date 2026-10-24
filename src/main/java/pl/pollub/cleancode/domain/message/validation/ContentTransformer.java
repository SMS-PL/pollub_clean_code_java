package pl.pollub.cleancode.domain.message.validation;

//[FunctionalInterface]
@FunctionalInterface
public interface ContentTransformer {
    String transform(String content);

    default ContentTransformer andThen(ContentTransformer next) {
        return content -> next.transform(transform(content));
    }
}
