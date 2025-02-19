package by.crashcar.email.notifier.listener;

import by.crashcar.core.dto.CreateOrderEvent;
import by.crashcar.email.notifier.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderListener {
    private final String createOrderTopic = "${topic.create-order}";
    private final EmailSender emailSender;

    @KafkaListener(topics = createOrderTopic, groupId = "email-notifier")
    public void createOrder(CreateOrderEvent orderEvent) {
        emailSender.send(orderEvent);
    }

}
