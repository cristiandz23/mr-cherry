package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.PaymentType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class PaymentRequest {

    private BigDecimal amount;
    @NotNull @NotEmpty
    private PaymentType paymentType;
    private OrderResponse order;

}
