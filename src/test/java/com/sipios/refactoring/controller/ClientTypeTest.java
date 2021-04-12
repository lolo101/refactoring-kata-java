package com.sipios.refactoring.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ClientTypeTest {

    @ParameterizedTest
    @CsvSource({"STANDARD_CUSTOMER, 100", "PREMIUM_CUSTOMER, 90", "PLATINUM_CUSTOMER, 50"})
    void discount_by_client_type(ClientType clientType, double expected) {
        Assertions.assertThat(clientType.applyDiscount(100)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"STANDARD_CUSTOMER, 201", "PREMIUM_CUSTOMER, 801", "PLATINUM_CUSTOMER, 2001"})
    void should_throw_on_excessive_price(ClientType clientType, double excessivePrice) {
        Assertions.assertThatThrownBy(() -> clientType.checkPriceLimit(excessivePrice))
            .isInstanceOf(PriceLimitException.class)
            .hasMessageContaining("Price (" + excessivePrice + ") is too high");
    }
}
