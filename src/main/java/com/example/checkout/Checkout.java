package com.example.checkout;

import com.example.item.Item;
import com.example.promotions.PromotionalRules;
import org.apache.commons.math3.util.Precision;

import java.util.ArrayList;
import java.util.List;

/**
 * Checkout is the main entity that holds all the items in the Basket. It also uses
 * {@link com.example.promotions.PromotionalRules} instance
 * to get the item level as well as basket level discounts.
 */
public class Checkout {

    /**
     * @see com.example.promotions.PromotionalRules instance
     */
    private final PromotionalRules promotionalRules;

    /**
     * List of items in Basket
     */
    private final List<Item> items = new ArrayList<>();

    /**
     * Basket Total
     */
    private Double total = 0.0;

    public Checkout(PromotionalRules promotionalRules) {
        this.promotionalRules = promotionalRules;
    }

    /**
     * It adds the item to Basket. If a same item is added twice, it removes the existing item,
     * creates a new item with updated quantity and adds the new item to the basket.
     * It also updates the basket total for each item added.
     *
     * @param item item to add to basket.
     */
    public void scan(Item item) {
        Item newItem;
        int currentIndex = items.indexOf(item);
        if (currentIndex >= 0) {
            Item oldItem = items.get(currentIndex);
            newItem = cloneItem(oldItem, oldItem.getQuantity() + 1);
            items.remove(currentIndex);
            items.add(currentIndex, newItem);
        } else {
            newItem = cloneItem(item, 1);
            items.add(newItem);
        }
        total += newItem.getPrice();
    }

    /**
     * It calculates all the item level and basket level promotions and
     * applies it to the basket total.
     *
     * @return total of all items after applying discounts
     */
    public Double getTotal() {
        Double totalItemDiscount = 0.0;
        for (Item item : items) {
            Double itemDiscount = promotionalRules.applyItemPromotion(item);
            totalItemDiscount += itemDiscount;
        }
        total -= totalItemDiscount;
        Double checkoutTotalDiscount = promotionalRules.applyCheckoutPromotion(total);
        total -= checkoutTotalDiscount;
        return Precision.round(total, 2);
    }

    /**
     * It clones the given item with given quantity. All other attributes of
     * the given item apart from 'quantity' are cloned as is.
     *
     * @param item item to clone with given quantity
     * @param quantity desired quantity of the given item
     * @return cloned item with given quantity.
     */
    private Item cloneItem(Item item, Integer quantity) {
        return Item
                .builder()
                .productCode(item.getProductCode())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(quantity)
                .build();
    }
}
