package by.crashcar.email.notifier.service;

import by.crashcar.core.dto.CreateOrderEvent;

public interface EmailSender {
    void send(CreateOrderEvent createOrderEvent);
}
