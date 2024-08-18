package com.khangng.notification_service.notification;

import com.khangng.notification_service.kafka.order.OrderConfirmation;
import com.khangng.notification_service.kafka.payment.PaymentConfirmation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    
    public void saveNotification(PaymentConfirmation paymentConfirmation) {
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .paymentConfirmation(paymentConfirmation)
                        .notificationDate(LocalDateTime.now())
                        .build()
        );
    }
    
    public void saveNotification(OrderConfirmation orderConfirmation) {
        notificationRepository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .orderConfirmation(orderConfirmation)
                        .notificationDate(LocalDateTime.now())
                        .build()
        );
    }
}
