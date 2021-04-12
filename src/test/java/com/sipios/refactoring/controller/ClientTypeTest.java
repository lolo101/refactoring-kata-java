package com.sipios.refactoring.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ClientTypeTest {

    @ParameterizedTest
    @CsvSource({"STANDARD_CUSTOMER, 1", "PREMIUM_CUSTOMER, 0.9", "PLATINUM_CUSTOMER, 0.5"})
    void discount_by_client_type(ClientType clientType, double expected) {
        Assertions.assertThat(clientType.discout()).isEqualTo(expected);
    }
}
