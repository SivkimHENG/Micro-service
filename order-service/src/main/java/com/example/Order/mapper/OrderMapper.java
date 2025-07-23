
package com.example.Order.mapper;

import com.example.Order.dto.OrderDto;
import com.example.Order.dto.OrderItemDto;
import com.example.Order.model.Order;
import com.example.Order.model.OrderPricing;
import com.example.Order.model.ShippingAddress;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * OrderMapper
 */

@Component
public class OrderMapper implements Function<Order, OrderDto> {

  private final OrderItemMapper orderItemMapper;
  private final OrderPricingMapper orderPricingMapper;
  private final BillingAddressMapper billingAddressMapper;
  private final ShippingAddressMapper shippingAddressMapper;

  public OrderMapper(OrderItemMapper orderItemMapper, OrderPricingMapper orderPricingMapper,
      BillingAddressMapper billingAddressMapper, ShippingAddressMapper shippingAddressMapper) {
    this.orderItemMapper = orderItemMapper;
    this.orderPricingMapper = orderPricingMapper;
    this.billingAddressMapper = billingAddressMapper;
    this.shippingAddressMapper = shippingAddressMapper;
  }

  @Override
  public OrderDto apply(Order order) {
    if (order == null) {
      throw new RuntimeException("Order cannot be null.");
    }
    return new OrderDto(
        order.getId(),
        order.getOrderNumber(),
        order.getCustomerId(),
        order.getStatus(),
        order.getOrderType(),
        mapItems(order),
        orderPricingMapper.apply(order.getOrderPricing()),
        shippingAddressMapper.apply(order.getShippingAddress()),
        billingAddressMapper.apply(order.getBillingAddress()),
        order.getCreatedAt(),
        order.getUpdatedAt(),
        order.getCompleteAt(),
        order.getCancelledAt(),
        order.getNotes(),
        order.getVersion());
  }

  private List<OrderItemDto> mapItems(Order order) {
    return order.getItems().stream().map(orderItemMapper).toList();
  }
}
