package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.PaymentStatus;
import com.MrCherry.app.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PaymentResponse {

    private Long id;
    private BigDecimal amount;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private Long orderId;
    private String url;

}
