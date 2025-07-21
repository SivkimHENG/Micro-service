
package com.example.Order.mapper;
import com.example.Order.dto.OrderDto;
import com.example.Order.request.CreateOrderItemRequest;
import com.example.Order.request.CreateOrderRequest;
import com.example.Order.model.Order;
import com.example.Order.model.OrderItems;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * OrderMapper
 */

@Mapper( componentModel =  "spring",uses = {
       OrderItemMapper.class,
 //      OrderPricingMapper.class,
  //     AddressMapper.class
},
      nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrderMapper {

  OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

  @Mapping(target = "items",source = "orderItems")
  @Mapping(target = "shippingAddress", source = "shippingAddress")
  OrderDto toDto(Order order);


  @Mapping(target = "orderItems", source = "items")
  @Mapping(target = "shippingAddress", source = "shippingAddress")
  Order toEntity(OrderDto dto);


  @Mapping(target = "id", ignore = true)
  @Mapping(target = "orderNumber", ignore = true)
  @Mapping(target = "status", constant = "PENDING")
  @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
  @Mapping(target = "completeAt", ignore = true)
  @Mapping(target = "cancelledAt", ignore = true)
  @Mapping(target = "orderItems", source = "items")
  Order fromCreateRequest(CreateOrderRequest request);


  List<OrderDto> toDtoList(List<Order> orders);
  List<Order> toEntityList(List<OrderDto> dtos);


  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt",ignore = true)
  @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
  void updateOrderFromDto(OrderDto dto, @MappingTarget Order order);


  @Mapping(target = "id",ignore = true)
  @Mapping(target = "order", ignore = true)
  OrderItems fromCreateItemRequest(CreateOrderItemRequest request);



}
