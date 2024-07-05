package fel.cvut.cz.ear.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fel.cvut.cz.ear.service.notification.NotificationStrategy;
import jakarta.persistence.*;

@Entity
@Table(name = "NOTIFICATION")
public class Notification extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "recipient_id", nullable = false)
    private Member recipient;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "message", nullable = false)
    private String message;

    @Transient
    private NotificationStrategy notificationStrategy;

    // Конструкторы и геттеры/сеттеры

    public NotificationStrategy getNotificationStrategy() {
        return notificationStrategy;
    }

    public void setNotificationStrategy(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy;
    }

    public Notification(Member recipient, String subject, String message, NotificationStrategy notificationStrategy) {
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
        this.notificationStrategy = notificationStrategy;
    }

    public void sendNotification() {
        notificationStrategy.sendNotification(recipient, subject, message);
    }

    public Notification() {
    }

    public Member getRecipient() {
        return recipient;
    }

    public void setRecipient(Member recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
