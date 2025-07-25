package com.example.Order.mapper.updateOrder;

import com.example.Order.dto.OrderDto;
import com.example.Order.enums.OrderStatus;
import com.example.Order.mapper.*;
import com.example.Order.model.Order;
import com.example.Order.model.OrderItems;
import com.example.Order.model.OrderPricing;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
public class UpdateOrderMapperImpl implements UpdateOrderMapper {

    private final ShippingAddressEntityMapper shippingAddressEntityMapper;
    private final BillingAddressEntityMapper billingAddressEntityMapper;
    private final OrderItemEntityMapper orderItemEntityMapper;

    public UpdateOrderMapperImpl(ShippingAddressEntityMapper shippingAddressEntityMapper, BillingAddressEntityMapper billingAddressEntityMapper, OrderItemEntityMapper orderItemEntityMapper) {
        this.shippingAddressEntityMapper = shippingAddressEntityMapper;
        this.billingAddressEntityMapper = billingAddressEntityMapper;
        this.orderItemEntityMapper = orderItemEntityMapper;
    }



    @Override
    public void updateEntity(Order existingOrder, OrderDto request) {
        if(existingOrder.getStatus() != OrderStatus.PENDING &&
           existingOrder.getStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Cannot update order details in status " + existingOrder.getStatus());
        }

        if (request.orderType() != null) {
            existingOrder.setOrderType(request.orderType());
        }
        if (request.notes() != null) {
            existingOrder.setNotes(request.notes());
        }
        existingOrder.setUpdatedAt(LocalDateTime.now());

        if (request.shippingAddress() != null) {
            if (existingOrder.getShippingAddress() != null) {
                shippingAddressEntityMapper.updateEntity(existingOrder.getShippingAddress(), request.shippingAddress());
            } else {
                existingOrder.setShippingAddress(shippingAddressEntityMapper.toEntity(request.shippingAddress()));
            }
        }
        if (request.billingAddress() != null) {
            if (existingOrder.getBillingAddress() != null) {
                billingAddressEntityMapper.updateEntity(existingOrder.getBillingAddress(), request.billingAddress());
            } else {
                existingOrder.setBillingAddress(billingAddressEntityMapper.toEntity(request.billingAddress()));
            }
        }


        if (request.items() != null) {
            existingOrder.getItems().clear();
            List<OrderItems> newItems = request.items().stream()
                    .map(orderItemEntityMapper::toEntity)
                    .peek(item -> item.setOrder(existingOrder))
                    .toList();
            existingOrder.getItems().addAll(newItems);
        }

        if (request.orderPricing() != null) {
            if (existingOrder.getOrderPricing() != null) {
                OrderPricing pricing = existingOrder.getOrderPricing();
                pricing.setSubtotal(request.orderPricing().subtotal());
                pricing.setTaxAmount(request.orderPricing().taxAmount());
                pricing.setDiscountAmount(request.orderPricing().DiscountAmount());
                pricing.setTotalAmount(request.orderPricing().totalAmount());
                pricing.setCurrency(request.orderPricing().currency());
            } else {
                OrderPricing pricing = new OrderPricing();
                pricing.setSubtotal(request.orderPricing().subtotal());
                pricing.setTaxAmount(request.orderPricing().taxAmount());
                pricing.setDiscountAmount(request.orderPricing().DiscountAmount());
                pricing.setTotalAmount(request.orderPricing().totalAmount());
                pricing.setCurrency(request.orderPricing().currency());
                existingOrder.setOrderPricing(pricing);
            }
        }


    }





}
