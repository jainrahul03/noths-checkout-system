package com.example.promotions.rules;

import com.example.item.Item;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BuyXOrMorePromotionRuleTest {

    private BuyXOrMorePromotionRule buyXOrMorePromotionRule;

    @Before
    public void setup() {
        buyXOrMorePromotionRule = BuyXOrMorePromotionRule
                .builder()
                .promotionCode("TCH001")
                .desc("Travel Card Holder Promotion")
                .minQuantity(2)
                .discountValue(0.75)
                .build();
    }

    @Test
    public void item_promotionRuleIsSatisfied_returnDiscountValue() {
        Item item = Item
                .builder()
                .productCode("001")
                .name("Travel Card Holder")
                .price(9.50)
                .quantity(2)
                .build();
        Double expectedDiscount = 1.5;

        Double actualDiscount = buyXOrMorePromotionRule.apply(item);

        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    public void item_promotionRuleIsNotSatisfied_returnZeroDiscountValue() {
        Item item = Item
                .builder()
                .productCode("001")
                .name("Travel Card Holder")
                .price(9.50)
                .quantity(1)
                .build();
        Double expectedDiscount = 0.0;

        Double actualDiscount = buyXOrMorePromotionRule.apply(item);

        assertEquals(expectedDiscount, actualDiscount);
    }
}
