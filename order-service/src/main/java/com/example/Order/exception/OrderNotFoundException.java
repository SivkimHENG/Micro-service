package com.example.Order.exception;

import java.util.UUID;

public class OrderNotFoundException extends ResourceNotFoundException {

    private final UUID orderId;
    private final String orderNumber;

    public OrderNotFoundException(String message) {
        super(message);
        this.orderId = null;
        this.orderNumber = null;
    }

    public OrderNotFoundException(String message, UUID orderId) {
        super(message);
        this.orderId = orderId;
        this.orderNumber = null;
    }

    public OrderNotFoundException(String message, String orderNumber) {
        super(message);
        this.orderId = null;
        this.orderNumber = orderNumber;
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.orderId = null;
        this.orderNumber = null;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }


}
