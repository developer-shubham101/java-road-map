package designPattern.factory;

// NotificationService.java - Product Interface
interface NotificationService {
    void sendNotification(String userId, String message);
}

// Concrete Products
class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String userId, String message) {
        System.out.println("Sending EMAIL to " + userId + ": " + message);
    }
}

class SMSNotificationService implements NotificationService {
    @Override
    public void sendNotification(String userId, String message) {
        System.out.println("Sending SMS to " + userId + ": " + message);
    }
}

// NotificationFactory.java - Creator (Abstract Factory)
abstract class NotificationFactory {
    // The Factory Method
    public abstract NotificationService createNotificationService();

    // You can also have common logic here
    public void send(String userId, String message) {
        NotificationService service = createNotificationService();
        service.sendNotification(userId, message);
    }
}

// Concrete Creators
class EmailNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new EmailNotificationService();
    }
}

class SMSNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new SMSNotificationService();
    }
}

// Main.java
public class Main {
    public static void main(String[] args) {
        NotificationFactory emailFactory = new EmailNotificationFactory();
        emailFactory.send("user123", "Your order has been confirmed!");

        NotificationFactory smsFactory = new SMSNotificationFactory();
        smsFactory.send("user456", "Your package is out for delivery!");

        // Example: Deciding factory at runtime
        String preferredChannel = "SMS"; // This could come from user preferences
        NotificationFactory chosenFactory;
        if ("EMAIL".equalsIgnoreCase(preferredChannel)) {
            chosenFactory = new EmailNotificationFactory();
        } else {
            chosenFactory = new SMSNotificationFactory();
        }
        chosenFactory.send("user789", "Special offer just for you!");
    }
}