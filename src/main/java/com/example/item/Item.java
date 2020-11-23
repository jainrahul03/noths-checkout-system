package com.example.item;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A representation of Basket item.
 */

@Getter
@Builder
@EqualsAndHashCode
public class Item {
    private final String productCode;
    private final String name;
    private final Double price;
    @EqualsAndHashCode.Exclude private final Integer quantity;
}
