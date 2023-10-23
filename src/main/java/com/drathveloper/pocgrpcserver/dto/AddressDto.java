package com.drathveloper.pocgrpcserver.dto;

public record AddressDto(
        String country,
        String city,
        String state,
        String address,
        String postalCode) {
}
