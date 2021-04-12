package com.sipios.refactoring.controller;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
import java.util.Optional;

class Body {

    private final Item[] items;
    private final ClientType type;

    @JsonCreator
    public Body(Item[] items, ClientType type) {
        this.items = items;
        this.type = type;
    }

    public double finalPrice(boolean discountPeriod) throws PriceLimitException {
        double itemsPrice = itemsPrice(discountPeriod);
        double discountedPrice = type.applyDiscount(itemsPrice);
        type.checkPriceLimit(discountedPrice);
        return discountedPrice;
    }

    private double itemsPrice(boolean discountPeriod) {
        return Arrays.stream(Optional.ofNullable(items).orElse(new Item[0]))
            .mapToDouble(it -> it.totalPrice(discountPeriod))
            .sum();
    }
}
