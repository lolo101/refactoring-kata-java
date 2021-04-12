package com.sipios.refactoring.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ItemTypeTest {

    @ParameterizedTest
    @CsvSource({"TSHIRT, 30", "DRESS, 50", "JACKET, 100"})
    void discount_by_client_type(ItemType itemType, double expected) {
        Assertions.assertThat(itemType.price()).isEqualTo(expected);
    }

}
