package pl.pollub.cleancode.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

//[UnitTest]
class RequestHistoryTest {
    private final RequestHistory history = RequestHistory.getInstance();

    @BeforeEach
    void setUp() {
        history.clear();
    }

    @Test
    void getInstanceAlwaysReturnsSameObject() {
        assertThat(RequestHistory.getInstance()).isSameAs(RequestHistory.getInstance());
    }

    @Test
    void recordAddsEntryWithGivenData() {
        history.record("POST", "/messages", "type=PAYMENT");

        assertThat(history.size()).isEqualTo(1);
        RequestHistoryEntry entry = history.getEntries().getFirst();
        assertThat(entry.httpMethod()).isEqualTo("POST");
        assertThat(entry.path()).isEqualTo("/messages");
        assertThat(entry.details()).isEqualTo("type=PAYMENT");
    }

    @Test
    void iteratorReturnsNewestEntryFirst() {
        history.record("GET", "/first", "");
        history.record("GET", "/second", "");
        history.record("GET", "/third", "");

        List<String> paths = new ArrayList<>();
        history.forEach(entry -> paths.add(entry.path()));

        assertThat(paths).containsExactly("/third", "/second", "/first");
    }

    @Test
    void iteratorThrowsWhenNoMoreEntries() {
        Iterator<RequestHistoryEntry> iterator = history.iterator();

        assertThat(iterator.hasNext()).isFalse();
        assertThatThrownBy(iterator::next).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void clearRemovesAllEntriesAndReturnedListIsCopy() {
        history.record("GET", "/", "");
        List<RequestHistoryEntry> entriesBeforeClear = history.getEntries();

        history.clear();

        assertThat(history.size()).isZero();
        assertThat(entriesBeforeClear).hasSize(1);
    }
}
