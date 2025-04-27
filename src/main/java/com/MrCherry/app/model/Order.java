package com.MrCherry.app.model;

import com.MrCherry.app.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Address address;
    private String name;
    private String phone;
    private LocalDate date;
    @OneToOne
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToMany
    private List<Product> products;
    private BigDecimal amount;
    private OrderStatus orderStatus;

}
