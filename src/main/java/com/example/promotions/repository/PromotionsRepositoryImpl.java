package com.example.promotions.repository;

import com.example.item.Item;
import com.example.promotions.rules.PromotionRule;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository that contains all the available promotions. Promotions can be
 * added for item as well as for basket total.
 */
@NoArgsConstructor
public class PromotionsRepositoryImpl implements PromotionsRepository {

    /**
     * Stores all item level promotions as key-value pairs where key is the item itself
     * and value is the list of all available promotions for the item.
     */
    private final Map<Item, List<PromotionRule<Item>>> itemPromotions = new HashMap<>();

    /**
     * Stores all the basket level promotions.
     */
    private final List<PromotionRule<Double>> checkoutPromotions = new ArrayList<>();


    /**
     * Get all the available promotions for the given item.
     *
     * @param item given item.
     * @return list of all promotions for the given item.
     */
    @Override
    public List<PromotionRule<Item>> getItemPromotions(Item item) {
        return itemPromotions.getOrDefault(item, new ArrayList<>());
    }

    /**
     * Get all the available promotions for basket total.
     *
     * @return list of all promotions for basket total.
     */
    @Override
    public List<PromotionRule<Double>> getCheckoutPromotions() {
        return checkoutPromotions;
    }

    /**
     * Add a new promotion for an item.
     *
     * @param item given item for which the promotion rule is being added.
     * @param promotion promotion rule for the given item.
     */
    @Override
    public void addItemPromotion(Item item, PromotionRule<Item> promotion) {
        List<PromotionRule<Item>> promotions = new ArrayList<>();
        if (itemPromotions.containsKey(item)) {
            promotions = itemPromotions.get(item);
        }
        promotions.add(promotion);
        itemPromotions.put(item, promotions);
    }

    /**
     * Add a new promotion for basket total.
     *
     * @param promotion promotion rule for basket total.
     */
    @Override
    public void addCheckoutPromotion(PromotionRule<Double> promotion) {
        checkoutPromotions.add(promotion);
    }
}
