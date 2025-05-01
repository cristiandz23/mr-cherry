package com.MrCherry.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "contact_information")
public class ContactInformation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String lastName;
    @Column(nullable = false)
    private String phone;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> address;

}
