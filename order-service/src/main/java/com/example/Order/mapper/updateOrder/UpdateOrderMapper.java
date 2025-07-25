package com.example.Order.mapper.updateOrder;

import com.example.Order.dto.OrderDto;
import com.example.Order.model.Order;
import org.springframework.stereotype.Component;

public interface UpdateOrderMapper {

    public void updateEntity(Order existingOrder, OrderDto request);


}
