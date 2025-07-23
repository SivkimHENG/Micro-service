package com.example.Order.enums;

public enum OrderStatus {
    PENDING("Order is pending confirmation"),
    CONFIRMED("Order has been confirmed"),
    SHIPPED("Order has been shipped"),
    DELIVERED("Order has been delivered"),
    CANCELLED("Order has been cancelled"),
    RETURNED("Order has been returned");

    private final String description;

    OrderStatus(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }


    public boolean isFinalStatus(){
        return this == DELIVERED || this == CANCELLED || this == RETURNED;
    }

    public boolean isModification() {
        return this == PENDING || this == CONFIRMED;
    }



}
