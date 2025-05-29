package com.example.Order.respository;

import com.example.Order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository  extends JpaRepository<Order, UUID> {



}
