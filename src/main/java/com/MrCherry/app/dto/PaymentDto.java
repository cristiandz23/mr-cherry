package com.MrCherry.app.dto;

import com.MrCherry.app.model.Order;
import com.MrCherry.app.model.enums.PaymentStatus;
import com.MrCherry.app.model.enums.PaymentType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentDto {

    private Long id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private PaymentType paymentType;
    private PaymentStatus paymentStatus;
    private OrderResponse order;

}
