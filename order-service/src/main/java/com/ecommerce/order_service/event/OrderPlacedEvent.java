package com.ecommerce.order_service.event;

public record OrderPlacedEvent(String orderNumber, String email) {
}
