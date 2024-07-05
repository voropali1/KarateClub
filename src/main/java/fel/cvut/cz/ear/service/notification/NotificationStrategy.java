package fel.cvut.cz.ear.service.notification;

import fel.cvut.cz.ear.model.Member;

public interface NotificationStrategy {
    void sendNotification(Member recipient, String subject, String message);
}
