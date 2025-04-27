package com.MrCherry.app.model;

import com.MrCherry.app.model.enums.PaymentType;
import jakarta.persistence.*;

@Entity
@Table
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    private Long idPaymentProvider;
    @OneToOne
    private Order order;
}
