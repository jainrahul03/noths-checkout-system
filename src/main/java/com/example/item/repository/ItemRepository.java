package com.example.item.repository;

import com.example.item.Item;

/**
 * A skeleton definition for repository of all items available.
 */
public interface ItemRepository {
    /**
     * Get an item by it's product code.
     *
     * @param productCode product code the item.
     * @return an item if the given product code exists in the repository otherwise null.
     */
    Item getItemByProductCode(String productCode);
}
