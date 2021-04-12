package com.sipios.refactoring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private final DiscountPeriodService summerDiscountPeriod;

    public ShoppingController(DiscountPeriodService summerDiscountPeriod) {
        this.summerDiscountPeriod = summerDiscountPeriod;
    }

    @PostMapping
    public String getPrice(@RequestBody Body b) {
        try {
            boolean discountPeriod = summerDiscountPeriod.isDiscountPeriod();
            double finalPrice = b.finalPrice(discountPeriod);
            return String.valueOf(finalPrice);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

class Body {

    private Item[] items;
    private ClientType type;

    public Body(Item[] is, ClientType t) {
        this.items = is;
        this.type = t;
    }

    public Body() {}

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    public ClientType getType() {
        return type;
    }

    public void setType(ClientType type) {
        this.type = type;
    }

    public double finalPrice(boolean discountPeriod) throws Exception {
        double itemsPrice = itemsPrice(discountPeriod);
        double discountedPrice = type.applyDiscount(itemsPrice);
        type.checkPriceLimit(discountedPrice);
        return discountedPrice;
    }
    public double itemsPrice(boolean discountPeriod) {
        return Arrays.stream(Optional.ofNullable(items).orElse(new Item[0]))
            .mapToDouble(it -> it.totalPrice(discountPeriod))
            .sum();
    }
}

class Item {

    private ItemType type;
    private int nb;

    public Item() {}

    public Item(ItemType type, int quantity) {
        this.type = type;
        this.nb = quantity;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }

    public double totalPrice(boolean discountPeriod) {
        return type.price(discountPeriod) * nb;
    }
}
