package com.khangng.payment_service.customer;

public record CustomerResponse (
    String id,
    String firstName,
    String lastName,
    String email
){
}
