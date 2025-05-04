package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.PaymentStatus;
import com.MrCherry.app.model.enums.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class PaymentRequest {

    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private OrderResponse order;

}
