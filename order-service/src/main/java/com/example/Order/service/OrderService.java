package com.example.Order.service;


import com.example.Order.dto.*;
import com.example.Order.enums.OrderStatus;
import com.example.Order.model.Order;
import com.example.Order.model.OrderItems;
import com.example.Order.respository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    public OrderDto createDraft(OrderDraftDto draft) {
        Order order = new Order();
        order.setStatus(OrderStatus.DRAFT);
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        List<OrderItems> items = draft.getItems()
                .stream()
                .map(itemDto -> {
                    OrderItems item = new OrderItems();
                    item.setOrder(order);
                    item.setProductId(itemDto.getProductId());
                    item.setQuantity(itemDto.getQuantity());
                    return item;
        }).toList();

        order.setItems(items);
        Order savedOrder = orderRepository.save(order);
        return mapToDto(savedOrder);
    }


    public OrderDto placeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found + " + orderId));


        if(order.getStatus() != OrderStatus.DRAFT){
            throw new IllegalArgumentException("Only Draft orders can be placed");
        }

        order.setStatus(OrderStatus.PLACED);
        order.setUpdatedAt(Instant.now());

        Order orderSaved = orderRepository.save(order);
        return mapToDto(orderSaved);
    }

    public OrderDto cancelOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found" + orderId));

        if(order.getStatus() != OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Order Already Cancelled");
        }
        order.setStatus(OrderStatus.CANCELLED);
        order.setUpdatedAt(Instant.now());

        Order orderSaved =  orderRepository.save(order);
        return mapToDto(orderSaved);

    }

    public StatusTimelineDto getStatusTimeline(UUID orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found" + orderId));

        StatusEventDto event = new StatusEventDto();
        event.setStatus(OrderStatus.valueOf(order.getStatus().name()));
        event.setTimestamp(order.getUpdatedAt());

        StatusTimelineDto timeline = new StatusTimelineDto();
        timeline.setOrderId(order.getId());
        timeline.setEvents(List.of(event));
        return timeline;
    }



    public OrderDto retryPayment(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found" + orderId));
        if(order.getStatus() == OrderStatus.PLACED){
            throw new IllegalArgumentException("Only PLACED ordered can retry payment");
        }


        boolean paymentSucceed = true;
        if(!paymentSucceed) {
            throw new IllegalArgumentException("Payment retry failed");
        }

        order.setUpdatedAt(Instant.now());
        Order orderSaved = orderRepository.save(order);
        return mapToDto(orderSaved);
    }

    private OrderDto mapToDto(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());

        List<OrderItemsDto> itemDtos = order.getItems().stream()
                .map(item -> {
                    OrderItemsDto i = new OrderItemsDto();
                    i.setId(item.getId());
                    i.setProductId(item.getProductId());
                    i.setQuantity(item.getQuantity());
                    i.setPrice(item.getPrice());
                    return i ;
                }).toList();

        dto.setItem(itemDtos);
        return dto;
    }

}
