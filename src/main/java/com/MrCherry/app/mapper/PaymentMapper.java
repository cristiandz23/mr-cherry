package com.MrCherry.app.mapper;

import com.MrCherry.app.dto.PaymentDto;
import com.MrCherry.app.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toPayment(PaymentDto paymentDto);

    PaymentDto toDto(Payment payment);

}
