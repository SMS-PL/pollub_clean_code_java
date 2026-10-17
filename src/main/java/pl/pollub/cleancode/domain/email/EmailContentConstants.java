package pl.pollub.cleancode.domain.email;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailContentConstants {

    public static final String PAYMENT_ISSUE_CONTENT= """
            Dear Customer,
           
            We have encountered an issue with your recent payment. Please contact our support team for assistance.
            
            Best regards,
            Payment Department
            """;
}
