package com.example.promotions;

import com.example.item.Item;
import com.example.item.repository.ItemRepository;
import com.example.promotions.repository.PromotionsRepository;
import com.example.promotions.rules.BuyXOrMorePromotionRule;
import com.example.promotions.rules.PromotionRule;
import com.example.promotions.rules.SpendXOrMorePromotionRule;

/**
 * Promotions initializer defines and loads all the promotions for an item or basket total.
 */
public class PromotionsInitializer {

    public static void initializePromotions(ItemRepository itemRepository, PromotionsRepository promotionsRepository) {
        //get the travel card holder item
        Item travelCardHolder = itemRepository.getItemByProductCode("001");

        //define a promotion rule
        PromotionRule<Item> travelCardHolderPromotion = BuyXOrMorePromotionRule
                .builder()
                .promotionCode("TCH2")
                .desc("Buy 2 or more Travel Card Holder for £8.50 each")
                .minQuantity(2)
                .discountValue(0.75)
                .build();

        //add item promotion rule to promotions repository
        promotionsRepository.addItemPromotion(travelCardHolder, travelCardHolderPromotion);

        //define promotion rule for basket total
        PromotionRule<Double> checkoutPromotion = SpendXOrMorePromotionRule
                .builder()
                .promotionCode("CHECKOUT60")
                .desc("Spend over £60 and get 10% off")
                .minAmount(60.0)
                .discountInPercentage(10.0)
                .build();

        //add checkout promotion rule to promotions repository
        promotionsRepository.addCheckoutPromotion(checkoutPromotion);
    }
}
