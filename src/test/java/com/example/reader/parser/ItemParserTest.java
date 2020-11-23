package com.example.reader.parser;

import com.example.item.Item;
import org.junit.Assert;
import org.junit.Test;

public class ItemParserTest {

    private final Parser<Item> parser = new ItemParser();

    @Test
    public void csvLine_itemParserIsInvoked_returnItem() {
        Item expected = Item
                .builder()
                .productCode("001")
                .name("Travel Card Holder")
                .price(9.25)
                .build();
        String csvLine = String.format("%s,%s,£%s", expected.getProductCode(), expected.getName(), expected.getPrice());

        Item actual = parser.parse(csvLine);

        Assert.assertEquals(expected.getProductCode(), actual.getProductCode());
        Assert.assertEquals(expected.getName(), actual.getName());
        Assert.assertEquals(expected.getPrice(), actual.getPrice());
    }
}
