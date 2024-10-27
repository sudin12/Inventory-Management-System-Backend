package com.inigne.ims.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    private final String accountSid = "**";
    private final String authToken = "**";
    private final String fromPhoneNumber = "**";
    private final String recipientPhoneNumber = "**";

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
    }

    public void sendLowStockAlert(String productName, int stockQuantity) {
        String alertMessage = "Low Stock Alert! Product: " + productName + " has only " + stockQuantity + " units left.";

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(recipientPhoneNumber),
                new com.twilio.type.PhoneNumber(fromPhoneNumber),
                alertMessage
        ).create();

        System.out.println("Low stock alert sent: " + message.getSid());
    }
}
