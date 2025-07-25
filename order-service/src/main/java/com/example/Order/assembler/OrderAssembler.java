package com.example.Order.assembler;

import com.example.Order.dto.OrderDto;
import com.example.Order.mapper.BillingAddressEntityMapper;
import com.example.Order.mapper.BillingAddressMapper;
import com.example.Order.mapper.OrderItemEntityMapper;
import com.example.Order.mapper.ShippingAddressEntityMapper;
import com.example.Order.model.*;
import org.mapstruct.ap.shaded.freemarker.cache.FileTemplateLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderAssembler {

    private final ShippingAddressEntityMapper shippingAddressEntityMapper;
    private final BillingAddressEntityMapper billingAddressEntityMapper;
    private final OrderItemEntityMapper orderItemEntityMapper;


    public OrderAssembler(ShippingAddressEntityMapper shippingAddressEntityMapper, BillingAddressEntityMapper billingAddressEntityMapper, OrderItemEntityMapper orderItemEntityMapper) {
        this.shippingAddressEntityMapper = shippingAddressEntityMapper;
        this.billingAddressEntityMapper = billingAddressEntityMapper;
        this.orderItemEntityMapper = orderItemEntityMapper;
    }


    public void enrich(Order order, OrderDto dto) {
        if(dto.shippingAddress() != null) {
            ShippingAddress shippingAddress = shippingAddressEntityMapper.toEntity(dto.shippingAddress());
            order.setShippingAddress(shippingAddress);
        }
        if(dto.billingAddress() != null) {
            BillingAddress billingAddress = billingAddressEntityMapper.toEntity(dto.billingAddress());
        }
        if(dto.items() != null && !dto.items().isEmpty()){
            List<OrderItems> items =
                    dto.items().stream()
                            .map(orderItemEntityMapper::toEntity)
                            .peek(item -> item.setOrder(order))
                            .toList();
        }


        if(dto.orderPricing() != null ) {
            OrderPricing pricing = new OrderPricing();
            pricing.setSubtotal(dto.orderPricing().subtotal());
            pricing.setTaxAmount(dto.orderPricing().taxAmount());
            pricing.setDiscountAmount(dto.orderPricing().DiscountAmount());
            pricing.setCurrency(dto.orderPricing().currency());
            pricing.setTotalAmount(dto.orderPricing().totalAmount());
            order.setOrderPricing(pricing);
        }











    }






}
