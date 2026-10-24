package pl.pollub.cleancode.common;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//[Singleton] //[ISP] //[Naming]
public final class RequestHistory implements RequestHistoryRecorder, RequestHistoryReader {

    private static volatile RequestHistory instance;

    private final List<RequestHistoryEntry> entries = new ArrayList<>();

    private RequestHistory() {
    }

    //[Singleton]
    public static RequestHistory getInstance() {
        if (instance == null) {
            synchronized (RequestHistory.class) {
                if (instance == null) {
                    instance = new RequestHistory();
                }
            }
        }
        return instance;
    }

    @Override
    public synchronized void record(String httpMethod, String path, String details) {
        entries.add(new RequestHistoryEntry(LocalDateTime.now(), httpMethod, path, details));
    }

    @Override
    public synchronized void clear() {
        entries.clear();
    }

    @Override
    public synchronized List<RequestHistoryEntry> getEntries() {
        return List.copyOf(entries);
    }

    @Override
    public synchronized int size() {
        return entries.size();
    }

    //[Iterator]
    @Override
    public Iterator<RequestHistoryEntry> iterator() {
        return new NewestFirstIterator(getEntries());
    }
}
