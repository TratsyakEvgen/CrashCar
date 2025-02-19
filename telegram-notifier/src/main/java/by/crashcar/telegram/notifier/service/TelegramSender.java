package by.crashcar.telegram.notifier.service;

import by.crashcar.core.dto.CreateOrderEvent;

public interface TelegramSender {
    void send(CreateOrderEvent createOrderEvent);
}
