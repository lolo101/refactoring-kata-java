package com.sipios.refactoring.controller;

public enum ItemType {
    TSHIRT(30),
    DRESS(50),
    JACKET(100);

    private final int price;

    ItemType(int price) {
        this.price = price;
    }

    public double price() {
        return price;
    }
}
