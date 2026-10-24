package pl.pollub.cleancode.common;

//[ISP]
public interface RequestHistoryRecorder {
    void record(String httpMethod, String path, String details);

    void clear();
}
