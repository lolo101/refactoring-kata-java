package com.sipios.refactoring.controller;

public enum ClientType {
    STANDARD_CUSTOMER(1.0),
    PREMIUM_CUSTOMER(0.9),
    PLATINUM_CUSTOMER(0.5);

    private final double discount;

    ClientType(double discount) {
        this.discount = discount;
    }

    public double discout() {
        return discount;
    }
}
