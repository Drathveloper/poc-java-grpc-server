package com.drathveloper.pocgrpcserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Client {
    private final Long id;
    private final String username;
    private final String fullName;
    private final String email;
    private final String phone;
    private final LocalDate birthDate;
    private final Address address;

    public Client(
            Long id,
            String username,
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDate birthDate,
            Address address) {
        this.id = id;
        this.username = username;
        this.fullName = firstName + " " + lastName;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
    }

}
