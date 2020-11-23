package com.example.checkout;

import com.example.item.Item;
import com.example.promotions.PromotionalRules;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckoutTest {

    @Mock
    PromotionalRules promotionalRules;

    private Checkout checkout;

    @Before
    public void setup() {
        checkout = new Checkout(promotionalRules);
    }

    @Test
    public void itemPromotion_itemIsScanned_promotionsAreAppliedAndCheckoutTotalIsUpdated() {
        List<Item> cartItems = getCartItems();
        Double total = 0.0;
        double itemDiscount = 5.0;
        Double checkoutDiscount = 0.0;

        when(promotionalRules.applyItemPromotion(any(Item.class))).thenReturn(itemDiscount);
        when(promotionalRules.applyCheckoutPromotion(any(Double.class))).thenReturn(checkoutDiscount);

        for (Item item: cartItems) {
            checkout.scan(item);
            total += item.getPrice();
        }

        Double expectedTotal = total - (itemDiscount * cartItems.size()) - checkoutDiscount;

        assertEquals(expectedTotal, checkout.getTotal());
        verify(promotionalRules, times(cartItems.size())).applyItemPromotion(any(Item.class));
        verify(promotionalRules).applyCheckoutPromotion(any(Double.class));
    }

    @Test
    public void itemAndCheckoutTotalPromotions_itemsAreScanned_promotionsAreAppliedAndCheckoutTotalIsUpdated() {
        Item travelCardHolder = Item.builder()
                .productCode("001")
                .name("Travel Card Holder")
                .price(9.25)
                .build();
        List<Item> cartItems = getCartItems();
        int n = cartItems.size();
        cartItems.add(travelCardHolder);
        Double total = 0.0;
        Double itemDiscount = 5.0;
        Double checkoutDiscount = 10.0;

        when(promotionalRules.applyItemPromotion(any(Item.class))).thenReturn(itemDiscount, 0.0, 0.0);
        when(promotionalRules.applyCheckoutPromotion(any(Double.class))).thenReturn(checkoutDiscount);

        for (Item item: cartItems) {
            checkout.scan(item);
            total += item.getPrice();
        }

        Double expectedTotal = total - itemDiscount - checkoutDiscount;

        assertEquals(expectedTotal, checkout.getTotal());
        verify(promotionalRules, times(n)).applyItemPromotion(any(Item.class));
        verify(promotionalRules).applyCheckoutPromotion(any(Double.class));
    }

    private List<Item> getCartItems() {
        List<Item> items = new ArrayList<>();
        items.add(Item.builder()
            .productCode("001")
            .name("Travel Card Holder")
            .price(9.25)
            .build()
        );

        items.add(Item.builder()
                .productCode("002")
                .name("Personalised cuff links")
                .price(45.00)
                .build()
        );

        items.add(Item.builder()
                .productCode("003")
                .name("Kids T-shirt ")
                .price(19.95)
                .build()
        );
        return items;
    }
}
