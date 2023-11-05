package com.drathveloper.pocgrpcserver.model;

import java.time.LocalDate;

public record Client(Long id, String username, String firstName, String lastName, String email, String phone,
                     LocalDate birthDate, Address address) {
}
