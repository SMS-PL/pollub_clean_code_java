package pl.pollub.cleancode.domain.email;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    //simple imitqation of sending email
    public void sendEmail(String emailAddress, String subject, String message){
        System.out.println("Sending email to "+emailAddress+" with subject "+subject+" and message "+message);
    }
}
