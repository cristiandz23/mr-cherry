package com.MrCherry.app.repository;

import com.MrCherry.app.model.Order;
import com.MrCherry.app.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByOrderStatus(OrderStatus orderStatus);


}
