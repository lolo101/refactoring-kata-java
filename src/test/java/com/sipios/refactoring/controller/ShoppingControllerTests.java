package com.sipios.refactoring.controller;

import com.sipios.refactoring.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static com.sipios.refactoring.controller.ClientType.STANDARD_CUSTOMER;

class ShoppingControllerTests extends UnitTest {

    @InjectMocks
    private ShoppingController controller;

    @Test
    void should_not_throw() {
        Assertions.assertDoesNotThrow(
            () -> controller.getPrice(new Body(new Item[] {}, STANDARD_CUSTOMER))
        );
    }

    @Test
    void should_throw_on_price_limit_exceeded() {
        Assertions.assertThrows(Exception.class,
            () -> controller.getPrice(new Body(new Item[] {new Item("JACKET", 3)}, STANDARD_CUSTOMER))
        );
    }
}
