package by.crashcar.email.notifier.service.impl;

import by.crashcar.core.dto.CreateOrderEvent;
import by.crashcar.email.notifier.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultEmailSender implements EmailSender {
    private final JavaMailSender mailSender;
    private final MailProperties mailProperties;
    @Value("${emails}")
    private String[] emails;

    @Override
    public void send(CreateOrderEvent createOrderEvent) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(emails);
        message.setSubject("Новый заказ CrashCar.by");
        message.setText(getText(createOrderEvent));
        mailSender.send(message);
    }

    private String getText(CreateOrderEvent createOrderEvent) {
        StringBuilder builder = new StringBuilder();
        builder.append("id: ").append(createOrderEvent.getId()).append("\n")
                .append("Телефон: ").append(createOrderEvent.getPhone()).append("\n")
                .append("Сообщение: ").append(createOrderEvent.getMessage()).append("\n")
                .append("Файлы:").append("\n");

        createOrderEvent.getLinks()
                .forEach(link -> builder.append(link).append("\n"));

        return builder.toString();
    }
}
