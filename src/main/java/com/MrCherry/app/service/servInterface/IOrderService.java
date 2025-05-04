package com.MrCherry.app.service.servInterface;

import com.MrCherry.app.dto.OrderRequest;
import com.MrCherry.app.dto.OrderResponse;
import com.MrCherry.app.model.Order;

import java.util.List;

public interface IOrderService {

    // CRUD BASICO
    OrderResponse create(OrderRequest orderRequest);
    void deleteById(Long id);
    OrderResponse findById(Long id);
    List<OrderResponse> findAll();
    Order findOrder(Long orderId);

//    List<OrderResponse> findByUser(Long userId);


    //PAGO DE LA ORDEN
    OrderResponse payOrder(Long id);
    //ACTUALIZACION DE ESTADO
//    void updateOrderStatus(Long orderId, OrderStatus newStatus);
    OrderResponse receiveOrder(Long orderId);
    OrderResponse cancelOrder(Long orderId);
    OrderResponse rejectOrder(Long orderId);
    OrderResponse deliverOrder(Long orderId);
    OrderResponse finishOrder(Long orderId);



}
