package fel.cvut.cz.ear.controller;

import fel.cvut.cz.ear.model.Member;
import fel.cvut.cz.ear.model.Notification;
import fel.cvut.cz.ear.service.NotificationService;
import fel.cvut.cz.ear.service.notification.NotificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody Notification notification, @Requesgit remote add origintParam("notificationMethod") String notificationMethod) {
        Member recipient = notification.getRecipient();
        String subject = notification.getSubject();
        String message = notification.getMessage();

        try {
            notificationService.sendNotificationToMember(recipient, subject, message, notificationMethod);
            return ResponseEntity.ok("Notification sent successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
