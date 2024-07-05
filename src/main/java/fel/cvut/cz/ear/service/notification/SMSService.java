package fel.cvut.cz.ear.service.notification;

public interface SMSService {
    void sendSMS(String phoneNumber, String message);
}
