package com.ecommerce.order_service.service;

import com.ecommerce.order_service.client.InventoryClient;
import com.ecommerce.order_service.dto.OrderRequest;
import com.ecommerce.order_service.event.OrderPlacedEvent;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if (!isProductInStock)
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in stock");

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())));
        order.setSkuCode(orderRequest.skuCode());
        order.setQuantity(orderRequest.quantity());

        orderRepository.save(order);

        //send message to kafka topic
        //orderNumber and email
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setOrderNumber(order.getOrderNumber());
        orderPlacedEvent.setEmail(orderRequest.userDetails().email());
        orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
        orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
        log.info("Start - Sending orderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
        log.info("End - Sending orderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);

    }

}
