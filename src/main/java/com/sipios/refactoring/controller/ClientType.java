package com.sipios.refactoring.controller;

public enum ClientType {
    STANDARD_CUSTOMER("standard customer", 1.0, 200),
    PREMIUM_CUSTOMER("premium customer", 0.9, 500),
    PLATINUM_CUSTOMER("platinum customer", 0.5, 2000);

    private final String name;
    private final double discount;
    private final double maximumPrice;

    ClientType(String name, double discount, double maximumPrice) {
        this.name = name;
        this.discount = discount;
        this.maximumPrice = maximumPrice;
    }

    public double applyDiscount(double itemsPrice) {
        return itemsPrice * discount;
    }

    public void checkPriceLimit(double p) throws PriceLimitException {
        if (p > maximumPrice) {
            throw new PriceLimitException(this, p);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
