package com.MrCherry.app.mapper;

import com.MrCherry.app.dto.OrderRequest;
import com.MrCherry.app.dto.OrderResponse;
import com.MrCherry.app.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse toResponse(Order order);

    OrderRequest toRequestFromOrder(Order order);
    @Mapping(source = "payment" , target = "payment")
    Order toOrderFromRequest(OrderRequest orderRequest);

    Order toOrderFromResponse(OrderResponse orderResponse);

}
