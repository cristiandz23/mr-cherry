package com.MrCherry.app.model;

import com.MrCherry.app.model.enums.DeliveryType;
import com.MrCherry.app.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Data
@Entity
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany
    private List<OrderItem> orderItems;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    @OneToOne
    private ContactInformation contactInformation;

    @OneToOne(orphanRemoval = false)
    @JoinColumn(name = "address_id",nullable = false)
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Payment payment;

}
