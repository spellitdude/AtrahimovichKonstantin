package com.example.userservice.service;

public @interface CircuitBreaker {
    String name();

    String fallbackMethod();
}
