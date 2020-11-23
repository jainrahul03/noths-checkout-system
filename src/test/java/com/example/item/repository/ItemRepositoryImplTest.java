package com.example.item.repository;

import com.example.exception.AppException;
import com.example.item.Item;
import com.example.reader.parser.ItemParser;
import com.example.reader.parser.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class ItemRepositoryImplTest {

    private ItemRepository itemRepository;

    @Before
    public void setup() throws AppException {
        Parser<Item> itemParser = new ItemParser();
        itemRepository = new ItemRepositoryImpl("src/test/resources/items.csv", itemParser);
    }

    @Test
    public void validProductCode_getItemByProductCodeIsInvoked_returnItem() {
        String expectedProductCode = "001";

        Item actual = itemRepository.getItemByProductCode(expectedProductCode);
        Assert.assertTrue(Objects.nonNull(actual));
        Assert.assertEquals(expectedProductCode, actual.getProductCode());
    }

    @Test
    public void invalidProductCode_getItemByProductCodeIsInvoked_returnEmptyResult() {
        Assert.assertFalse(Objects.nonNull(itemRepository.getItemByProductCode("004")));
    }
}
