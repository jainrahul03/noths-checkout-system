package com.example.reader.parser;

import com.example.item.Item;

/**
 * Item parser reads a line from a CSV file and transforms it into the given return type.
 */
public class ItemParser implements Parser<Item> {

    /**
     * Parsing the given CSV line and transforming it into an Item object.
     *
     * @param line a string representation of a single line in a file
     * @return an object of the given type
     */
    @Override
    public Item parse(String line) {
        String[] logData = line.split(",");
        return Item
                .builder()
                .productCode(logData[0])
                .name(logData[1])
                .price(Double.parseDouble(logData[2].replace("£", "")))
                .build();
    }
}
