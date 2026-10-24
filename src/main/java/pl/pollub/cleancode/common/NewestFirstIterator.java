package pl.pollub.cleancode.common;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

//[Iterator]
public class NewestFirstIterator implements Iterator<RequestHistoryEntry> {
    private final List<RequestHistoryEntry> entries;
    private int currentIndex;

    public NewestFirstIterator(List<RequestHistoryEntry> entries) {
        this.entries = entries;
        this.currentIndex = entries.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return currentIndex >= 0;
    }

    @Override
    public RequestHistoryEntry next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Brak kolejnych wpisów w historii");
        }
        return entries.get(currentIndex--);
    }
}
