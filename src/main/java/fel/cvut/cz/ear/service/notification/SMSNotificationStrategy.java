package fel.cvut.cz.ear.service.notification;

import fel.cvut.cz.ear.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SMSNotificationStrategy implements NotificationStrategy {

    @Autowired
    private SMSService smsService; // Предположим, что у вас есть сервис для отправки SMS

    @Override
    public void sendNotification(Member recipient, String subject, String message) {
        smsService.sendSMS(recipient.getPhoneNumber(), message);
    }
}
