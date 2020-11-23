package com.example.promotions.repository;

import com.example.item.Item;
import com.example.promotions.rules.BuyXOrMorePromotionRule;
import com.example.promotions.rules.PromotionRule;
import com.example.promotions.rules.SpendXOrMorePromotionRule;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PromotionsRepositoryImplTest {

    private PromotionsRepository promotionsRepository;
    private BuyXOrMorePromotionRule itemPromotionRule;
    private SpendXOrMorePromotionRule checkoutPromotionRule;

    @Before
    public void setup() {
        promotionsRepository = new PromotionsRepositoryImpl();
        addItemPromotions();
        addCheckoutPromotions();
    }

    private void addItemPromotions() {
        Item item = Item
                .builder()
                .productCode("001")
                .name("Travel Card Holder")
                .price(9.50)
                .build();
        itemPromotionRule = BuyXOrMorePromotionRule
                .builder()
                .promotionCode("TCH2")
                .desc("Travel Card Holder Promotion")
                .minQuantity(2)
                .discountValue(0.75)
                .build();
        BuyXOrMorePromotionRule itemPromotionRuleTwo = BuyXOrMorePromotionRule
                .builder()
                .promotionCode("TCH4")
                .desc("Travel Card Holder Promotion")
                .minQuantity(4)
                .discountValue(1.0)
                .build();
        promotionsRepository.addItemPromotion(item, itemPromotionRule);
        promotionsRepository.addItemPromotion(item ,itemPromotionRuleTwo);
    }

    private void addCheckoutPromotions() {
        checkoutPromotionRule = SpendXOrMorePromotionRule
                .builder()
                .promotionCode("CHECKOUT60")
                .desc("Checkout Promotion")
                .minAmount(60.0)
                .discountInPercentage(10.0)
                .build();
        promotionsRepository.addCheckoutPromotion(checkoutPromotionRule);
    }

    @Test
    public void validProductCode_getItemPromotionByProductCodeIsInvoked_returnItemPromotion() {
        Item item = Item
                .builder()
                .productCode("001")
                .name("Travel Card Holder")
                .price(9.50)
                .build();

        List<PromotionRule<Item>> promotions = promotionsRepository.getItemPromotions(item);

        assertFalse(promotions.isEmpty());
        PromotionRule<Item> promotionRule = promotions.get(0);
        assertTrue(promotionRule instanceof BuyXOrMorePromotionRule);
        BuyXOrMorePromotionRule promotion = (BuyXOrMorePromotionRule) promotionRule;
        assertEquals(itemPromotionRule.getPromotionCode(), promotion.getPromotionCode());
        assertEquals(itemPromotionRule.getDesc(), promotion.getDesc());
        assertEquals(itemPromotionRule.getMinQuantity(), promotion.getMinQuantity());
        assertEquals(itemPromotionRule.getDiscountValue(), promotion.getDiscountValue());
    }

    @Test
    public void invalidCheckoutP_getItemPromotionByProductCodeIsInvoked_returnEmptyPromotion() {
        Item item = Item
                .builder()
                .productCode("002")
                .name("Personalised cuff links")
                .price(45.00)
                .build();

        List<PromotionRule<Item>> promotions = promotionsRepository.getItemPromotions(item);

        assertTrue(promotions.isEmpty());
    }

    @Test
    public void checkoutPromotionExists_getItemPromotionByProductCodeIsInvoked_returnCheckoutPromotion() {
        List<PromotionRule<Double>> promotions = promotionsRepository.getCheckoutPromotions();

        assertFalse(promotions.isEmpty());
        PromotionRule<Double> promotionRule = promotions.get(0);
        assertTrue(promotionRule instanceof SpendXOrMorePromotionRule);
        SpendXOrMorePromotionRule promotion = (SpendXOrMorePromotionRule) promotionRule;
        assertEquals(checkoutPromotionRule.getPromotionCode(), promotion.getPromotionCode());
        assertEquals(checkoutPromotionRule.getDesc(), promotion.getDesc());
        assertEquals(checkoutPromotionRule.getMinAmount(), promotion.getMinAmount());
        assertEquals(checkoutPromotionRule.getDiscountInPercentage(), promotion.getDiscountInPercentage());
    }

    @Test
    public void checkoutPromotionDoesNotExists_getItemPromotionByProductCodeIsInvoked_returnEmptyPromotion() {
        promotionsRepository = new PromotionsRepositoryImpl();
        List<PromotionRule<Double>> promotions = promotionsRepository.getCheckoutPromotions();

        assertTrue(promotions.isEmpty());
    }
}
