package com.example.promotions.repository;

import com.example.item.Item;
import com.example.promotions.rules.PromotionRule;

import java.util.List;

/**
 * A skeleton definition for repository of all promotions available.
 */
public interface PromotionsRepository {
    /**
     * Get all the available promotions for the given item.
     *
     * @param item an item in the basket.
     * @return list of all promotions for the given item.
     */
    List<PromotionRule<Item>> getItemPromotions(Item item);

    /**
     * Get all the available promotions for basket total.
     *
     * @return list of all promotions for basket total.
     */
    List<PromotionRule<Double>> getCheckoutPromotions();

    /**
     * Add a new promotion for an item.
     *
     * @param item an item for which the promotion is being added.
     * @param promotion promotion rule for the given item.
     */
    void addItemPromotion(Item item, PromotionRule<Item> promotion);

    /**
     * Add a new promotion for basket total.
     *
     * @param promotion promotion rule for basket total.
     */
    void addCheckoutPromotion(PromotionRule<Double> promotion);
}
