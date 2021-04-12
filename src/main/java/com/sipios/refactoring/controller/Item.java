package com.sipios.refactoring.controller;

import com.fasterxml.jackson.annotation.JsonCreator;

class Item {

    private final ItemType type;
    private final int nb;

    @JsonCreator
    public Item(ItemType type, int nb) {
        this.type = type;
        this.nb = nb;
    }

    public double totalPrice(boolean discountPeriod) {
        return type.price(discountPeriod) * nb;
    }
}
