package com.example.promotions.rules;

import com.example.item.Item;
import lombok.Builder;
import lombok.Getter;

/**
 * A promotion rule when buying X or more quantity of an item will have a fixed
 * value price drop discount. An example would be: Buy 2 or more quantity of T-shirt and
 * get a price drop of £2 per T-shirt.
 */
@Getter
public class BuyXOrMorePromotionRule extends PromotionRule<Item> {
    /**
     * Minimum quantity of an item i.e. X.
     */
    private final Integer minQuantity;

    /**
     * Fixed value price drop discount.
     */
    private final Double discountValue;

    @Builder
    public BuyXOrMorePromotionRule(String promotionCode, String desc, Integer minQuantity, Double discountValue) {
        super(promotionCode, desc);
        this.minQuantity = minQuantity;
        this.discountValue = discountValue;
    }

    /**
     * Calculating the discount if applicable.
     *
     * @param item item in basket for which the promotion is being applied.
     * @return total discount for the given item (if any).
     */
    @Override
    public Double apply(Item item) {
        double itemDiscount = 0.0;
        if (item.getQuantity() >= minQuantity) {
            itemDiscount = discountValue * item.getQuantity();
        }
        return itemDiscount;
    }
}
