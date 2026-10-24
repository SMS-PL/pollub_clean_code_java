package pl.pollub.cleancode.common;

import java.util.List;

//[ISP]
public interface RequestHistoryReader extends Iterable<RequestHistoryEntry> {
    List<RequestHistoryEntry> getEntries();

    int size();
}
