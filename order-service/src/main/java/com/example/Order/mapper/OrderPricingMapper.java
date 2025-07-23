package com.example.Order.mapper;

import com.example.Order.dto.OrderPricingDto;
import com.example.Order.model.OrderPricing;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OrderPricingMapper  implements Function<OrderPricing, OrderPricingDto> {



    @Override
    public OrderPricingDto apply(OrderPricing orderPricing) {

        return new OrderPricingDto(
                orderPricing.getSubtotal(),
                orderPricing.getTaxAmount(),
                orderPricing.getDiscountAmount(),
                orderPricing.getTotalAmount(),
                orderPricing.getCurrency() );
    }




}
