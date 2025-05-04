package com.MrCherry.app.mapper;

import com.MrCherry.app.dto.OrderItemDto;
import com.MrCherry.app.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

//    OrderItem toOrderItem(OrderItemDto orderItemDto);
    @Mapping(source="product.id",target = "productId")
    OrderItemDto toDto(OrderItem orderItem);

}
