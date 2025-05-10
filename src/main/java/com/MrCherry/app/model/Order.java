package com.MrCherry.app.model;

import com.MrCherry.app.model.enums.DeliveryType;
import com.MrCherry.app.model.enums.OrderStatus;
import com.MrCherry.app.model.enums.PaymentType;
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


//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "order_id", nullable = false) // Esta es la CLAVE
//    private List<OrderItem> orderItems;
// Relación bidireccional: Un Order tiene varios OrderItems
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_information_id")
    private ContactInformation contactInformation;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id",nullable = false)
//    private Address deliveryAddress;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", nullable = false)
    private Address deliveryAddress;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    private Payment payment;

}
