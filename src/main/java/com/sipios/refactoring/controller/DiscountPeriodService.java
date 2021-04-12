package com.sipios.refactoring.controller;

import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class DiscountPeriodService {
    private final Calendar cal;

    public DiscountPeriodService(Calendar cal) {
        this.cal = cal;
    }

    public boolean isDiscountPeriod() {
        return summerDiscountPeriod() || winterDiscountPeriod();
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
