package com.MrCherry.app.mapper;

import com.MrCherry.app.dto.PaymentRequest;
import com.MrCherry.app.dto.PaymentResponse;
import com.MrCherry.app.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(source = "order.id",target ="orderId")
     PaymentResponse toResponse(Payment payment);

}
