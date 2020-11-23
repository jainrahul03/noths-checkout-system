package com.example.promotions.rules;

import lombok.Getter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Getter
public class SpendXOrMorePromotionRuleTest {

    private SpendXOrMorePromotionRule spendXOrMorePromotionRule;

    @Before
    public void setup() {
        spendXOrMorePromotionRule = SpendXOrMorePromotionRule
                .builder()
                .promotionCode("CP001")
                .desc("Checkout Promotion")
                .minAmount(60.0)
                .discountInPercentage(10.0)
                .build();
    }

    @Test
    public void item_promotionRuleIsSatisfied_returnDiscountValue() {
        Double checkoutTotal = 60.0;
        Double expectedDiscount = 6.0;

        Double actualDiscount = spendXOrMorePromotionRule.apply(checkoutTotal);

        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    public void item_promotionRuleIsNotSatisfied_returnZeroDiscountValue() {
        Double checkoutTotal = 59.9;
        Double expectedDiscount = 0.0;

        Double actualDiscount = spendXOrMorePromotionRule.apply(checkoutTotal);

        assertEquals(expectedDiscount, actualDiscount);
    }
}
