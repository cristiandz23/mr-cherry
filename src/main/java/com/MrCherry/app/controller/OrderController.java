package com.MrCherry.app.controller;


import com.MrCherry.app.dto.OrderRequest;
import com.MrCherry.app.dto.OrderResponse;
import com.MrCherry.app.service.servInterface.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order/api/")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @PostMapping("create")
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest orderRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(orderRequest));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteById(Long id){
        orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Order with id "+ id +" was deleted");
    }

    @GetMapping("get-by-id/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findById(id));
    }

    @GetMapping("get-all")
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    @GetMapping("get-all-payed")
    public ResponseEntity<List<OrderResponse>> findAllPayed(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.finOrderPayed());
    }

    @PutMapping("/order-received/{id}")
    ResponseEntity<OrderResponse> receiveOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.receiveOrder(id));
    }

    @PutMapping("/order-canceled/{id}")
    ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.cancelOrder(id));
    }

    @PutMapping("/order-delivered/{id}")
    ResponseEntity<OrderResponse> deliverOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.deliverOrder(id));
    }
    @PutMapping("/order-finished/{id}")
    ResponseEntity<OrderResponse> finishOrder(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.finishOrder(id));
    }







}
