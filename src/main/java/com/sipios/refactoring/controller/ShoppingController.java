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
        try {
            boolean discountPeriod = summerDiscountPeriod.isDiscountPeriod();
            double finalPrice = b.finalPrice(discountPeriod);
            return String.valueOf(finalPrice);
        } catch (PriceLimitException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}

