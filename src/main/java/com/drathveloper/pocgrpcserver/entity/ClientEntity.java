package com.drathveloper.pocgrpcserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "client")
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private LocalDate birthDate;

    @OneToOne(cascade = CascadeType.ALL)
    private AddressEntity address;
}
