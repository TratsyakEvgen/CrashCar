package by.crashcar.orders.service;

import by.crashcar.orders.dto.CreateOrder;

public interface OrderService {
    long create(CreateOrder createOrder);
}
