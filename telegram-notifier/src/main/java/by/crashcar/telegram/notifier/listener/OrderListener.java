package by.crashcar.telegram.notifier.listener;

import by.crashcar.core.dto.CreateOrderEvent;
import by.crashcar.telegram.notifier.service.TelegramSender;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderListener {
    private final String createOrderTopic = "${topic.create-order}";
    private final TelegramSender telegramSender;

    @KafkaListener(topics = createOrderTopic, groupId = "telegram-notifier")
    public void createOrder(CreateOrderEvent createOrderEvent) {
        telegramSender.send(createOrderEvent);
    }
}
