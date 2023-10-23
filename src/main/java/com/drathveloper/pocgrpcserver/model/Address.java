package com.drathveloper.pocgrpcserver.model;

public record Address(
        Long id, String country, String city, String state, String address, String postalCode) {
}
