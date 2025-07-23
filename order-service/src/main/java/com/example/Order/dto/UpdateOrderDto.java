package com.example.Order.dto;

import com.example.Order.model.ShippingAddress;

public record UpdateOrderDto(
        ShippingAddressDto shippingAddress,
        BillingAddressDto billingAddress,
        String notes
) {
}
