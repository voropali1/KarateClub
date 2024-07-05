package fel.cvut.cz.ear.service.notification;

import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InternalNotificationStrategy implements NotificationStrategy {

    @Autowired
    private NotificationService notificationService; // Предположим, что у вас есть сервис для внутренних уведомлений

    @Override
    public void sendNotification(Member recipient, String subject, String message) {
        notificationService.sendInternalNotification(recipient, subject, message);
    }
}
