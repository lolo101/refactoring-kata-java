package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import static com.sipios.refactoring.controller.ClientType.STANDARD_CUSTOMER;
import static com.sipios.refactoring.controller.ItemType.JACKET;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Mock
    private DiscountPeriodService discountPeriodService;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[]{}, STANDARD_CUSTOMER)));
    }

    @Test
    void should_throw_on_price_limit_exceeded() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> controller.getPrice(new Body(new Item[]{new Item(JACKET, 3)}, STANDARD_CUSTOMER)));
    }

    @ParameterizedTest
    @CsvSource({"STANDARD_CUSTOMER, 100.0", "PREMIUM_CUSTOMER, 90.0", "PLATINUM_CUSTOMER, 50.0"})
    void should_return_discounted_price(ClientType clientType, String discountedPrice) {
        Assertions.assertEquals(discountedPrice, controller.getPrice(new Body(new Item[]{new Item(JACKET, 1)}, clientType)));
    }

    @ParameterizedTest
    @CsvSource({"STANDARD_CUSTOMER, 90.0", "PREMIUM_CUSTOMER, 81.0", "PLATINUM_CUSTOMER, 45.0"})
    void should_return_discounted_price_with_discount_period(ClientType clientType, String discountedPrice) {
        Mockito.when(discountPeriodService.isDiscountPeriod()).thenReturn(true);

        Assertions.assertEquals(discountedPrice, controller.getPrice(new Body(new Item[]{new Item(JACKET, 1)}, clientType)));
    }
}
