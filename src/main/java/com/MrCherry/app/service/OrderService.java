package com.MrCherry.app.service;


import com.MrCherry.app.dto.OrderRequest;
import com.MrCherry.app.dto.OrderResponse;
import com.MrCherry.app.mapper.OrderMapper;
import com.MrCherry.app.model.Order;
import com.MrCherry.app.model.enums.OrderStatus;
import com.MrCherry.app.repository.OrderRepository;
import com.MrCherry.app.service.servInterface.IOrderItemService;
import com.MrCherry.app.service.servInterface.IOrderService;
import com.MrCherry.app.service.servInterface.IProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.MrCherry.app.model.enums.OrderStatus.*;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IProductService productService;


    @Override
    public OrderResponse create(OrderRequest orderRequest) {
        if(orderRequest.getContactInformation() == null && orderRequest.getUser() == null)
            throw new RuntimeException("UNO TIENE QUE SER NO NULL");

        Order newOrder = orderMapper.toOrderFromRequest(orderRequest);
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setOrderStatus(CREATED);
        newOrder.setAmount(orderItemService.calculateAmount(orderRequest.getOrderItems()));
        //IMPLEMENTAR METODO getSameMenu en OrderItemService
        newOrder.setOrderItems(orderItemService.getSameMenu(orderRequest.getOrderItems()));
        newOrder = orderRepository.save(newOrder);
        return orderMapper.toResponse(newOrder);
    }

    @Override
    public void deleteById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                ()->new RuntimeException("No se encontro"));
        if(isValidElimination(order.getOrderStatus()))
            throw new RuntimeException("No se puede eliminar exception");
        orderRepository.delete(order);

    }
    private boolean isValidElimination(OrderStatus current){
        return switch (current){
            case REJECTED, DELIVERED, CREATED -> true;
            default -> false;
        };
    }

    @Override
    public OrderResponse findByIs(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                ()->new RuntimeException("No se encontro"));
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> findAll() {
        List<OrderResponse> orderResponses = new ArrayList<>();
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty())
            throw new RuntimeException("No se encontraron ordenes");

        for(Order order : orders){
            orderResponses.add(orderMapper.toResponse(order));
        }
        return orderResponses;
    }

//    @Override
//    public List<OrderResponse> findByUser(Long userId) {
//        UserResponse user = userService.findById(userId);
//        return List.of();
//    }



    private OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId).orElseThrow(
                ()->new RuntimeException("No se encontro"));
        if (!isValidTransition(order.getOrderStatus(), newStatus)) {
            throw new RuntimeException("IllegalStateException/Invalid state transition: " + order.getOrderStatus() + " → " + newStatus);
        }
        order.setOrderStatus(newStatus);
       return orderMapper.toResponse(orderRepository.save(order));

    }



    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        return switch (current) {
            case CREATED -> List.of(PAYED, CANCELED, REJECTED).contains(next);
            case PAYED ->   List.of(PROCESS,CANCELED).contains(next);
            case PROCESS -> List.of(READY, CANCELED).contains(next);//ANALIZAR SI ES POSIBLE CANCEAR Y APLICAR LA LOGICA CORRESPONDIENTE
            case READY -> List.of(SENT, DELIVERED).contains(next);
            case SENT -> List.of(DELIVERED).contains(next);
            case CANCELED, REJECTED -> List.of(PROCESS).contains(next);
            default -> false;

        };
    }

    @Override
    @Transactional
    public OrderResponse payOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                ()->new RuntimeException("No se encontro"));
        productService.SaleProccess(order.getOrderItems());
        return updateOrderStatus(order.getId(), PAYED);

    }

    @Override
    public OrderResponse receiveOrder(Long orderId) {
        return this.updateOrderStatus(orderId, PROCESS);
    }

    @Override
    @Transactional
    public OrderResponse cancelOrder(Long orderId) {
        OrderResponse order = this.updateOrderStatus(orderId, CANCELED);
        //oprtimizar esto
        Order order1 = orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("no se encotro"));
        productService.returnProcess(order1.getOrderItems());
        return order;

    }

    @Override
    public OrderResponse rejectOrder(Long orderId) {
        return this.updateOrderStatus(orderId, REJECTED);
    }

    @Override
    public OrderResponse deliverOrder(Long orderId) {
        return this.updateOrderStatus(orderId, DELIVERED);
    }

    @Override
    public OrderResponse finishOrder(Long orderId) {
        return this.updateOrderStatus(orderId, READY);
    }



}
