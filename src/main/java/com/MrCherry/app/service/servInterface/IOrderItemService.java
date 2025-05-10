package com.MrCherry.app.service.servInterface;

import com.MrCherry.app.dto.OrderItemDto;
import com.MrCherry.app.model.Order;
import com.MrCherry.app.model.OrderItem;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public interface IOrderItemService {

    BigDecimal calculateAmount(List<OrderItem> orderItems);

    List<OrderItem> getSameMenu(@NotNull List<OrderItemDto> orderItems,Order order);

}
