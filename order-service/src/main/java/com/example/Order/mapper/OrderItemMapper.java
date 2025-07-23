package com.example.Order.mapper;


import com.example.Order.dto.OrderDto;
import com.example.Order.dto.OrderItemDto;
import com.example.Order.exception.IllegalOrderStateException;
import com.example.Order.model.Order;
import com.example.Order.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class OrderItemMapper implements Function<OrderItems,OrderItemDto> {




    @Override
    public OrderItemDto apply(OrderItems item) {
        if(item == null) {
            throw new IllegalOrderStateException("OrderItem cannot be null.");
        }

        return new OrderItemDto(
                item.getId(),
                item.getProductId(),
                item.getProductName(),
                item.getProductSku(),
                item.getQuantity(),
                item.getPrice(),
                item.getDiscountAmount(),
                item.getSpecialInstructions());

    }

}
