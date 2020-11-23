package com.example.promotions;

import com.example.item.Item;
import com.example.promotions.repository.PromotionsRepository;
import com.example.promotions.rules.PromotionRule;

import java.util.List;

/**
 * PromotionalRules calculates the maximum discounts for all the promotions available for item(s)
 * or basket.
 */
public class PromotionalRules {
    /**
     * Promotions repository to get all the promotions for a given item or basket.
     */
    private final PromotionsRepository promotionsRepository;

    public PromotionalRules(PromotionsRepository promotionsRepository) {
        this.promotionsRepository = promotionsRepository;
    }

    /**
     * Calculate maximum discount for all the available promotions for the given item.
     * Maximum discount is calculated by calculating individual discounts for each
     * available promotion and returning the maximum of those individual discounts.
     *
     * @param item item in basket for which the promotion is being applied.
     * @return maximum discount available for the given item.
     */
    public Double applyItemPromotion(Item item) {
        Double maxDiscount = 0.0;
        List<PromotionRule<Item>> promotions = promotionsRepository.getItemPromotions(item);
        for (PromotionRule<Item> pr: promotions) {
            Double discount = pr.apply(item);
            if (discount > maxDiscount) {
                maxDiscount = discount;
            }
        }
        return maxDiscount;
    }

    /**
     * Calculate maximum discount for all the available promotions for the given basket total.
     * Maximum discount is calculated by calculating individual discounts for each
     * available promotion and returning the maximum of those individual discounts.
     *
     * @param checkoutTotal basket total for which the promotion is being applied.
     * @return maximum discount available for the given basket total.
     */
    public Double applyCheckoutPromotion(Double checkoutTotal) {
        Double maxDiscount = 0.0;
        List<PromotionRule<Double>> promotions = promotionsRepository.getCheckoutPromotions();
        for (PromotionRule<Double> pr: promotions) {
            Double discount = pr.apply(checkoutTotal);
            if (discount > maxDiscount) {
                maxDiscount = discount;
            }
        }
        return maxDiscount;
    }
}
