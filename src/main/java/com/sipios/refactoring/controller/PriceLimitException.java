package com.sipios.refactoring.controller;

public class PriceLimitException extends Exception {
    public PriceLimitException(ClientType type, double price) {
        super("Price (" + price + ") is too high for " + type);
    }
}
