package by.crashcar.orders.service.impl;

import by.crashcar.core.dto.CreateOrderEvent;
import by.crashcar.orders.dto.CreateOrder;
import by.crashcar.orders.entity.Media;
import by.crashcar.orders.entity.Order;
import by.crashcar.orders.mapper.OrderMapper;
import by.crashcar.orders.repository.OrderRepository;
import by.crashcar.orders.service.MediaService;
import by.crashcar.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DefaultOrderService implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final MediaService mediaService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${topic.create-order}")
    private String createOrderTopic;

    @Override
    public long create(CreateOrder createOrder) {
        Order order = orderMapper.toOrder(createOrder);
        orderRepository.save(order);
        long orderId = order.getId();
        List<Media> media = mediaService.save(orderId, createOrder.getFiles());
        order.setMedia(media);
        CreateOrderEvent createOrderEvent = orderMapper.toCreateOrderMessage(order);
        kafkaTemplate.send(createOrderTopic, String.valueOf(order.getId()), createOrderEvent);
        return orderId;
    }
}
