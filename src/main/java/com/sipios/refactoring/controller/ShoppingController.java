package com.sipios.refactoring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private final DiscountPeriodService summerDiscountPeriod;

    public ShoppingController(DiscountPeriodService summerDiscountPeriod) {
        this.summerDiscountPeriod = summerDiscountPeriod;
    }

    @PostMapping
    public String getPrice(@RequestBody Body b) {
        double p = 0;

        // Compute discount for customer
        double clientDiscout = b.getType().discout();

        if (b.getItems() == null) {
            return "0";
        }
        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        boolean discountPeriod = summerDiscountPeriod.isDiscountPeriod();
        for (int i = 0; i < b.getItems().length; i++) {
            Item it = b.getItems()[i];
            p += it.getType().price(discountPeriod) * it.getNb() * clientDiscout;
        }

        try {
            b.getType().checkPriceLimit(p);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(p);
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
}
