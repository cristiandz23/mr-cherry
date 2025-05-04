package com.MrCherry.app.dto;

import com.MrCherry.app.model.enums.PaymentStatus;
import com.MrCherry.app.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@NoArgsConstructor
@Data
public class PaymentDto {

    private Long id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private OrderResponse order;

}
