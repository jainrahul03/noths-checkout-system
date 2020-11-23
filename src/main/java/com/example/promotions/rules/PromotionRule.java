package com.example.promotions.rules;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An abstract promotion rule that all promotion rules must conform to.
 *
 * @param <T> parameterized object type on which promotion rule should be applied.
 * T could be an {@link com.example.item.Item} for an item promotion
 * or {@link java.lang.Double} for a basket promotion.
 */
@Getter
@AllArgsConstructor
public abstract class PromotionRule<T> {
    /**
     * Unique code for a promotion.
     */
    private final String promotionCode;

    /**
     * Description for a promotion.
     */
    private final String desc;

    /**
     * Abstract method for calculating the discount.
     *
     * @param obj object for which the promotion will be applied.
     * @return total discount (if any).
     */
    public abstract Double apply(T obj);
}
