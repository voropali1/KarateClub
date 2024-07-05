package fel.cvut.cz.ear.service.notification;

import org.springframework.stereotype.Component;

@Component
public class DefaultSMSService implements SMSService {

    @Override
    public void sendSMS(String phoneNumber, String message) {
        // Реализация отправки SMS
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
        // Здесь вы можете реализовать логику отправки SMS через сторонний сервис или симуляцию
    }
}
