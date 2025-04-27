package com.MrCherry.app.model;


import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String district;
    private String street;
    private String number;
    private String floor;
    private String department;

}
