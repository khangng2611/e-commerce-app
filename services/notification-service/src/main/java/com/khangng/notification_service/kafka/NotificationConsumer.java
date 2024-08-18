package com.khangng.notification_service.kafka;

import com.khangng.notification_service.kafka.order.OrderConfirmation;
import com.khangng.notification_service.kafka.payment.PaymentConfirmation;
import com.khangng.notification_service.mail.MailService;
import com.khangng.notification_service.notification.NotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationConsumer {
    private final NotificationService notificationService;
    private final MailService mailService;
    @KafkaListener(topics = "order-topic", groupId = "orderGroup")
    public void consumeOrderConfirmation(OrderConfirmation orderConfirmation) throws MessagingException {
        notificationService.saveNotification(orderConfirmation);
        // send order confirm email
        mailService.sendOrderConfirmationEmail(orderConfirmation);
    }
    
    @KafkaListener(topics = "payment-topic", groupId = "paymentGroup")
    public void consumePaymentConfirmation(PaymentConfirmation paymentConfirmation) throws MessagingException {
        notificationService.saveNotification(paymentConfirmation);
        // send payment confirm email
        mailService.sendPaymentConfirmationEmail(paymentConfirmation);
        
    }
}
