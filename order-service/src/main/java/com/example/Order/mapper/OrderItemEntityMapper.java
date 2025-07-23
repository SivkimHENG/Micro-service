package com.example.Order.mapper;

import com.example.Order.dto.OrderItemDto;
import com.example.Order.model.OrderItems;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class OrderItemEntityMapper {
    public OrderItems toEntity(OrderItemDto dto) {
        if(dto == null){
            return null;
        }

        OrderItems orderItems = new OrderItems();
        orderItems.setId(dto.id());
        orderItems.setProductId(dto.productId());
        orderItems.setProductName(dto.productName());
        orderItems.setProductSku(dto.productSku());
        orderItems.setQuantity(dto.quantity());
        orderItems.setPrice(dto.price());
        orderItems.setDiscountAmount(dto.discountAmount());
        orderItems.setSpecialInstructions(dto.specialInstruction());

        return orderItems;
    }

    public void updateEntity(OrderItems entity, OrderItemDto dto) {
        if(entity == null || entity == null) {
            return;
        }
        entity.setProductId(dto.productId());
        entity.setProductName(dto.productName());
        entity.setProductSku(dto.productSku());
        entity.setQuantity(dto.quantity());
        entity.setPrice(dto.price());
        entity.setDiscountAmount(dto.discountAmount());
        entity.setSpecialInstructions(dto.specialInstruction());
    }
}
