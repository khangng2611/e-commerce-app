package com.khangng.notification_service.mail;

import com.khangng.notification_service.kafka.order.OrderConfirmation;
import com.khangng.notification_service.kafka.payment.PaymentConfirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class MailService {
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    
    @Async
    public void sendOrderConfirmationEmail (OrderConfirmation orderConfirmation) throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            
            Map<String, Object> variables = new HashMap<>();
            variables.put("customerName", orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName());
            variables.put("orderId", orderConfirmation.orderId());
            variables.put("orderReference", orderConfirmation.orderReference());
            variables.put("products", orderConfirmation.products());
            variables.put("totalAmount", orderConfirmation.totalAmount());
            variables.put("paymentMethod", orderConfirmation.paymentMethod());
            
            // thymeleaf
            Context context = new Context();
            context.setVariables(variables);
            String htmlTemplate = templateEngine.process("order-confirmation.html", context);
            
            helper.setFrom("contact@ecom.vn");
            helper.addTo(orderConfirmation.customer().email());
            helper.setSubject("Order Confirmation");
            helper.setText(htmlTemplate, true);
            
            mailSender.send(message);
            log.info(String.format("INFO - Order Confirmation Email successfully sent to %s", orderConfirmation.customer().email()));
        } catch (MessagingException e) {
            throw new MessagingException("Failed to send email", e);
        }
    }
    
    @Async
    public void sendPaymentConfirmationEmail (PaymentConfirmation paymentConfirmation) throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
            
            Map<String, Object> variables = new HashMap<>();
            variables.put("customerName", paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname());
            variables.put("amount", paymentConfirmation.amount());
            variables.put("orderId", paymentConfirmation.orderId());
            variables.put("orderReference", paymentConfirmation.orderReference());
            variables.put("paymentMethod", paymentConfirmation.paymentMethod());
            
            // thymeleaf
            Context context = new Context();
            context.setVariables(variables);
            String htmlTemplate = templateEngine.process("payment-confirmation.html", context);
            
            helper.setFrom("contact@ecom.vn");
            helper.addTo(paymentConfirmation.customerEmail());
            helper.setSubject("Successful Payment Confirmation");
            helper.setText(htmlTemplate, true);
            
            mailSender.send(message);
            log.info(String.format("INFO - Payment Email successfully sent to %s", paymentConfirmation.customerEmail()));
        } catch (MessagingException e) {
            throw new MessagingException("Failed to send email", e);
        }
    }
}
