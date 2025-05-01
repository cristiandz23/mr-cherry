package com.MrCherry.app.model;

import com.MrCherry.app.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false,name = "user")
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(cascade = CascadeType.ALL,optional = false)
    private ContactInformation contactInformation;
    private LocalDate createdAt;
    @OneToMany(mappedBy = "user",orphanRemoval = false)
    private List<Order> orders;

}
