package pl.pollub.cleancode.common;

import java.time.LocalDateTime;

public record RequestHistoryEntry(LocalDateTime calledAt, String httpMethod, String path, String details) {
}
