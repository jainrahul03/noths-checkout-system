package com.example.item.repository;

import com.example.exception.AppException;
import com.example.item.Item;
import com.example.reader.CSVReader;
import com.example.reader.Reader;
import com.example.reader.parser.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A repository that contains all the items from 'items.csv' file in the resources.
 */
public class ItemRepositoryImpl implements ItemRepository {
    /**
     * Item repository for storing all items as key-value pairs where key is the item's product code
     * and value is the item itself.
     */
    private final Map<String, Item> itemRepository = new HashMap<>();

    /**
     * A Reader for reading all items from 'items.csv' file in the resources.
     */
    private final Reader<Item> reader;

    public ItemRepositoryImpl(String csvFilePath, Parser<Item> parser) throws AppException {
        this.reader = new CSVReader<>(csvFilePath, parser);
        loadItems();
    }

    /**
     * Read the 'items.csv' file from resources and store all the items as
     * key-value pairs in the item repository
     *
     * @throws AppException custom exception for errors while reading the given file.
     */
    private void loadItems() throws AppException {
        List<Item> items = reader.readFile();
        items.forEach(item -> itemRepository.put(item.getProductCode(), item));
    }

    /**
     * Get an item by it's product code.
     *
     * @param productCode product code of the given item to find in repository.
     * @return an item if the given product code exists in the repository otherwise null.
     */
    @Override
    public Item getItemByProductCode(String productCode) {
        return itemRepository.get(productCode);
    }
}
