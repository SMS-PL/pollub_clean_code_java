package pl.pollub.cleancode.common;

// Wspólna klasa bazowa dla wyjątków biznesowych - kontrolery pokazują ich komunikat użytkownikowi
public abstract class DomainException extends RuntimeException {
    protected DomainException(String message) {
        super(message);
    }
}
