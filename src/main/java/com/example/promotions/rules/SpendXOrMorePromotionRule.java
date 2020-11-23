package com.example.promotions.rules;

import lombok.Builder;
import lombok.Getter;

/**
 * A promotion rule when spending X or more amount will have a fixed
 * value percentage discount. An example would be: Spend £60 or more and get 10% discount.
 */
@Getter
public class SpendXOrMorePromotionRule extends PromotionRule<Double> {
    /**
     * Minimum amount that should be spent.
     */
    private final Double minAmount;

    /**
     * Fixed value percentage discount.
     */
    private final Double discountInPercentage;

    @Builder
    public SpendXOrMorePromotionRule(String promotionCode, String desc, Double minAmount, Double discountInPercentage) {
        super(promotionCode, desc);
        this.minAmount = minAmount;
        this.discountInPercentage = discountInPercentage;
    }

    /**
     * Calculates total discount on the amount spent if applicable.
     *
     * @param amount given basket total amount
     * @return total discount on the amount spent (if any).
     */
    @Override
    public Double apply(Double amount) {
        double discount = 0.0;
        if (amount >= minAmount) {
            discount = amount * (discountInPercentage / 100.0);
        }
        return discount;
    }
}
