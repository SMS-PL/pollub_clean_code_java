package pl.pollub.cleancode.domain.email;

//[LSP]
public class EmailService {
    //simple imitation of sending email
    public void sendEmail(String emailAddress, String subject, String message){
        System.out.println("Sending email to "+emailAddress+" with subject "+subject+" and message "+message);
    }
}
