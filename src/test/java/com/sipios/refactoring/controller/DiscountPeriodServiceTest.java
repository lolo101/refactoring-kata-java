package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiscountPeriodServiceTest extends UnitTest {

    @InjectMocks
    private DiscountPeriodService discountPeriodService;

    @Mock
    private Calendar calendar;

    @ParameterizedTest
    @CsvSource({"6, 1", "14, 6"})
    void should_be_true(int day, int month) {
        Mockito.when(calendar.get(Calendar.DAY_OF_MONTH)).thenReturn(day);
        Mockito.when(calendar.get(Calendar.MONTH)).thenReturn(month - 1);

        assertTrue(discountPeriodService.isDiscountPeriod());
    }

    @ParameterizedTest
    @CsvSource({"6, 2", "14, 5"})
    void should_be_false(int day, int month) {
        Mockito.when(calendar.get(Calendar.DAY_OF_MONTH)).thenReturn(day);
        Mockito.when(calendar.get(Calendar.MONTH)).thenReturn(month - 1);

        assertFalse(discountPeriodService.isDiscountPeriod());
    }
}
