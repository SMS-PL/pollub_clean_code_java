package pl.pollub.cleancode.domain.email;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmailContentConstants {

    public static final String PAYMENT_ISSUE_CONTENT= """
            Dear Payment Department, we have encountered an issue with a recent payment. Please contact our support team for assistance.
            """;

    public static final String SHIPPING_ISSUE_CONTENT = """
            Dear Shipping Department, a customer reported a problem with a delivery. Please check the shipment status.
            """;

    public static final String SCHEDULED_EMAIL_SUBJECT = "Scheduled Email";
}
