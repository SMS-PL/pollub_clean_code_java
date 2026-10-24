package pl.pollub.cleancode.domain.email;

import java.util.concurrent.atomic.AtomicInteger;

//[LSP]
public class CountingEmailService extends EmailService {
    private final AtomicInteger sentEmailsCount = new AtomicInteger();

    @Override
    public void sendEmail(String emailAddress, String subject, String message) {
        super.sendEmail(emailAddress, subject, message);
        sentEmailsCount.incrementAndGet();
    }

    public int getSentEmailsCount() {
        return sentEmailsCount.get();
    }
}
