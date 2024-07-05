package fel.cvut.cz.ear.service;

import fel.cvut.cz.ear.dao.NotificationDao;
import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Notification;
import fel.cvut.cz.ear.service.notification.EmailNotificationStrategy;
import fel.cvut.cz.ear.service.notification.InternalNotificationStrategy;
import fel.cvut.cz.ear.service.notification.NotificationStrategy;
import fel.cvut.cz.ear.service.notification.SMSNotificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationRepository;

    @Autowired
    private EmailNotificationStrategy emailNotificationStrategy;

    @Autowired
    private SMSNotificationStrategy smsNotificationStrategy;

    @Autowired
    private InternalNotificationStrategy internalNotificationStrategy;

    public void sendNotificationToMember(Member recipient, String subject, String message, String notificationMethod) {
        NotificationStrategy strategy;

        switch (notificationMethod) {
            case "EMAIL":
                strategy = emailNotificationStrategy;
                break;
            case "SMS":
                strategy = smsNotificationStrategy;
                break;
            case "INTERNAL":
                strategy = internalNotificationStrategy;
                break;
            default:
                throw new IllegalArgumentException("Unsupported notification method: " + notificationMethod);
        }

        Notification notification = new Notification(recipient, subject, message, strategy);
        notificationRepository.save(notification);

        // Отправка уведомления
        notification.sendNotification();
    }

    public void sendInternalNotification(Member recipient, String subject, String message) {
       ///
        System.out.println("Sending internal notification to " + recipient.getName());
    }
}
