package com.MrCherry.app.service;

import com.MrCherry.app.dto.OrderItemDto;
import com.MrCherry.app.model.Order;
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
    public BigDecimal calculateAmount(List<OrderItem> orderItems) {
        BigDecimal total = new BigDecimal(0);
        for(OrderItem orderItemDto : orderItems){
            total = total.add(orderItemDto.getProduct().getPrice().multiply(new BigDecimal(orderItemDto.getQuantity())));
        }

        return total;
    }

    @Override
    public List<OrderItem> getSameMenu(List<OrderItemDto> orderItems,Order newOrder) {
        List<OrderItem> items = new ArrayList<>();
        orderItems.forEach(order -> {
            OrderItem item = OrderItem.builder()
                    .product(productService.findProduct(order.getProductId()))
                    .quantity(order.getQuantity())
                    .order(newOrder)
                    .build();
            items.add(item);
        });
        return items;
    }
}
