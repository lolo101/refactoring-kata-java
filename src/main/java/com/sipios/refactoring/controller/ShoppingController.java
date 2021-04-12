package com.sipios.refactoring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;

import static com.sipios.refactoring.controller.ItemType.*;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private final Calendar cal;

    public ShoppingController(Calendar cal) {
        this.cal = cal;
    }

    @PostMapping
    public String getPrice(@RequestBody Body b) {
        double p = 0;

        // Compute discount for customer
        double d = b.getType().discout();

        if (b.getItems() == null) {
            return "0";
        }
        // Compute total amount depending on the types and quantity of product and
        // if we are in winter or summer discounts periods
        if (summerDiscountPeriod() || winterDiscountPeriod()) {
            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];

                if (it.getType() == TSHIRT) {
                    p += 30 * it.getNb() * d;
                } else if (it.getType() == DRESS) {
                    p += 50 * it.getNb() * 0.8 * d;
                } else if (it.getType() == JACKET) {
                    p += 100 * it.getNb() * 0.9 * d;
                }
            }
        } else {
            for (int i = 0; i < b.getItems().length; i++) {
                Item it = b.getItems()[i];
                p += it.getType().price() * it.getNb() * d;
            }
        }

        try {
            b.getType().checkPriceLimit(p);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return String.valueOf(p);
    }

    private boolean summerDiscountPeriod() {
        return cal.get(Calendar.DAY_OF_MONTH) < 15 &&
            cal.get(Calendar.DAY_OF_MONTH) > 5 &&
            cal.get(Calendar.MONTH) == Calendar.JUNE;
    }

    private boolean winterDiscountPeriod() {
        return cal.get(Calendar.DAY_OF_MONTH) < 15 &&
            cal.get(Calendar.DAY_OF_MONTH) > 5 &&
            cal.get(Calendar.MONTH) == Calendar.JANUARY;
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
