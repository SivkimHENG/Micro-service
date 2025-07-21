package com.example.Order.mapper;


import com.example.Order.dto.OrderDto;
import com.example.Order.dto.OrderItemDto;
import com.example.Order.model.Order;
import com.example.Order.model.OrderItems;
import com.example.Order.request.CreateOrderItemRequest;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {


    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    OrderItemDto toDto(OrderItems orderItem);

    @Mapping(target = "order", ignore = true) // Will be set by parent mapper
    OrderItems toEntity(OrderItemDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItems fromCreateRequest(CreateOrderItemRequest request);

    List<OrderItemDto> toDtoList(List<OrderItems> orderItems);
    List<OrderItems> toEntityList(List<OrderItemDto> dtos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    void updateOrderItemFromDto(OrderItemDto dto, @MappingTarget OrderItems orderItem);






}
