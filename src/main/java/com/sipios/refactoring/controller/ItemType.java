package com.sipios.refactoring.controller;

public enum ItemType {
    TSHIRT(30, 1.0),
    DRESS(50, 0.8),
    JACKET(100, 0.9);

    private final double price;
    private final double discount;

    ItemType(double price, double discount) {
        this.price = price;
        this.discount = discount;
    }

    public double price(boolean discountPeriod) {
        if (discountPeriod) {
            return price * discount;
        }
        return price;
    }
}
