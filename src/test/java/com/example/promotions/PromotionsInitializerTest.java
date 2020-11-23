package com.example.promotions;

import com.example.item.Item;
import com.example.item.repository.ItemRepository;
import com.example.promotions.repository.PromotionsRepository;
import com.example.promotions.rules.PromotionRule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class PromotionsInitializerTest {

    @Test
    public void itemAndPromotionsRepository_initializePromotionsIsInvoked_populatePromotions() {
        ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
        Item item = Mockito.mock(Item.class);
        PromotionsRepository promotionsRepository = Mockito.mock(PromotionsRepository.class);

        when(itemRepository.getItemByProductCode(anyString())).thenReturn(item);
        doNothing().when(promotionsRepository).addItemPromotion(eq(item), any(PromotionRule.class));
        doNothing().when(promotionsRepository).addCheckoutPromotion(any(PromotionRule.class));

        PromotionsInitializer.initializePromotions(itemRepository, promotionsRepository);

        verify(itemRepository).getItemByProductCode(anyString());
        verify(promotionsRepository).addItemPromotion(eq(item), any(PromotionRule.class));
        verify(promotionsRepository).addCheckoutPromotion(any(PromotionRule.class));
    }
}
