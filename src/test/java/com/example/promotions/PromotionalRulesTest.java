package com.example.promotions;

import com.example.item.Item;
import com.example.promotions.repository.PromotionsRepositoryImpl;
import com.example.promotions.rules.BuyXOrMorePromotionRule;
import com.example.promotions.rules.PromotionRule;
import com.example.promotions.rules.SpendXOrMorePromotionRule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PromotionalRulesTest {

    @Mock private PromotionsRepositoryImpl promotionsRepositoryImpl;
    @Mock private List<PromotionRule<Item>> mockItemPromotions;
    @Mock private Iterator<PromotionRule<Item>> mockItemIterator;
    @Mock private List<PromotionRule<Double>> mockCheckoutPromotions;
    @Mock private Iterator<PromotionRule<Double>> mockCheckoutIterator;

    private PromotionalRules promotionalRules;

    @Before
    public void setup() {
        promotionalRules = new PromotionalRules(promotionsRepositoryImpl);
    }

    @Test
    public void itemPromotion_applyIsInvoked_returnDiscountValue() {
        Item travelCardHolderItem = Item
                .builder()
                .productCode("001")
                .name("Travel Card Holder")
                .price(9.5)
                .quantity(2)
                .build();
        PromotionRule<Item> mockPromotion = Mockito.mock(BuyXOrMorePromotionRule.class);
        Double discountOne = 3.0;
        Double discountTwo = 6.0;

        when(promotionsRepositoryImpl.getItemPromotions(travelCardHolderItem)).thenReturn(mockItemPromotions);
        when(mockItemPromotions.iterator()).thenReturn(mockItemIterator);
        when(mockItemIterator.hasNext()).thenReturn(true, true, false);
        when(mockItemIterator.next()).thenReturn(mockPromotion);
        when(mockPromotion.apply(travelCardHolderItem)).thenReturn(discountOne, discountTwo);

        Double actualDiscount = promotionalRules.applyItemPromotion(travelCardHolderItem);

        assertEquals(discountTwo, actualDiscount);
    }

    @Test
    public void noItemPromotion_applyIsInvoked_returnZeroDiscountValue() {
        Item travelCardHolderItem = Item
                .builder()
                .productCode("001")
                .name("Travel Card Holder")
                .price(9.5)
                .quantity(2)
                .build();
        Double expectedDiscount = 0.0;

        when(promotionsRepositoryImpl.getItemPromotions(travelCardHolderItem)).thenReturn(new ArrayList<>());

        Double actualDiscount = promotionalRules.applyItemPromotion(travelCardHolderItem);

        assertEquals(expectedDiscount, actualDiscount);
    }

    @Test
    public void checkoutPromotion_applyIsInvoked_returnDiscountValue() {
        PromotionRule<Double> mockPromotion = Mockito.mock(SpendXOrMorePromotionRule.class);
        Double checkoutTotal = 50.0;
        Double discountOne = 5.0;
        Double discountTwo = 10.0;

        when(promotionsRepositoryImpl.getCheckoutPromotions()).thenReturn(mockCheckoutPromotions);
        when(mockCheckoutPromotions.iterator()).thenReturn(mockCheckoutIterator);
        when(mockCheckoutIterator.hasNext()).thenReturn(true, true, false);
        when(mockCheckoutIterator.next()).thenReturn(mockPromotion);
        when(mockPromotion.apply(checkoutTotal)).thenReturn(discountOne, discountTwo);

        Double actualDiscount = promotionalRules.applyCheckoutPromotion(checkoutTotal);

        assertEquals(discountTwo, actualDiscount);
    }

    @Test
    public void noCheckoutPromotion_applyIsInvoked_returnDiscountValue() {
        Double checkoutTotal = 50.0;
        Double expectedDiscount = 0.0;

        when(promotionsRepositoryImpl.getCheckoutPromotions()).thenReturn(new ArrayList<>());

        Double actualDiscount = promotionalRules.applyCheckoutPromotion(checkoutTotal);

        assertEquals(expectedDiscount, actualDiscount);
    }
}
