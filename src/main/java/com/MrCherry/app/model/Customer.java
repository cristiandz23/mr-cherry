package com.MrCherry.app.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
    private List<Order> orders;

}
