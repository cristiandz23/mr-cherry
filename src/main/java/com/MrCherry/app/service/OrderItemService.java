package com.MrCherry.app.service;

import com.MrCherry.app.dto.OrderItemDto;
import com.MrCherry.app.model.OrderItem;
import com.MrCherry.app.model.Product;
import com.MrCherry.app.repository.OrderItemRepository;
import com.MrCherry.app.service.servInterface.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService implements IOrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ProductService productService;
    @Override
    public BigDecimal calculateAmount(List<OrderItemDto> orderItems) {
        BigDecimal total = new BigDecimal(0);
        for(OrderItemDto orderItemDto : orderItems){
            total = total.add(productService.findById(orderItemDto.getProductId()).getPrice());
        }

        return total;
    }

    @Override
    public List<OrderItem> getSameMenu(List<OrderItemDto> orderItems) {
        List<OrderItem> items = new ArrayList<>();
        OrderItem item = new OrderItem();
        orderItems.forEach(order -> {
            item.setProduct(productService.findProduct(order.getProductId()));
            item.setQuantity(order.getQuantity());
            items.add(item);
        });
        return items;
    }
}
