package com.MrCherry.app.service.servInterface;

import com.MrCherry.app.dto.OrderItemDto;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderItemService {
    BigDecimal calculateAmount(List<OrderItemDto> orderItems);
}
