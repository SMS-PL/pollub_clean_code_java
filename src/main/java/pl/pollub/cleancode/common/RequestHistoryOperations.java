package pl.pollub.cleancode.common;

import java.util.Iterator;
import java.util.List;

//[ISP]
@Deprecated
public interface RequestHistoryOperations {
    void record(String httpMethod, String path, String details);

    void clear();

    List<RequestHistoryEntry> getEntries();

    int size();

    Iterator<RequestHistoryEntry> iterator();

    String exportAsCsv();

    void importFromCsv(String csv);
}
