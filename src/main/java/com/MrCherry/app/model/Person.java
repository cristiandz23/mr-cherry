package com.MrCherry.app.model;


import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String phone;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
//    @OneToOne // Esto indica que esta relación es controlada por 'Person
//    private User user;
//    @OneToOne
//    private Customer customer;


}
