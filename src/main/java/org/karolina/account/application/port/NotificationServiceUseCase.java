package org.karolina.account.application.port;

public interface NotificationServiceUseCase {
    void sendNotification(String message, long id);
}
