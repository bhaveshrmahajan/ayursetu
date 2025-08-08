package com.ayursetu.notification.exception;

public class NotificationFailedException extends RuntimeException {
    
    public NotificationFailedException(String message) {
        super(message);
    }
    
    public NotificationFailedException(String notificationId, String reason) {
        super(String.format("Notification failed for ID: %s. Reason: %s", notificationId, reason));
    }
}






