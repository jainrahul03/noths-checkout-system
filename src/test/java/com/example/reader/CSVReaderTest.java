package com.example.reader;

import com.example.exception.AppException;
import com.example.item.Item;
import com.example.reader.parser.Parser;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CSVReaderTest {

    @Mock
    private Parser<Item> parser;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void validCSVFile_readIsInvoked_returnListOfRecords() throws AppException {
        List<Item> expectedItems = buildItems();
        List<String> expectedItemsStr = expectedItems
                .stream()
                .map(item -> String.format("%s,%s,£%s", item.getProductCode(), item.getName(), item.getPrice()))
                .collect(Collectors.toList());

        String testDataFilePath = "src/test/resources/items.csv";
        Reader<Item> csvReader = new CSVReader<>(testDataFilePath, parser);

        when(parser.parse(anyString()))
                .thenReturn(expectedItems.get(0))
                .thenReturn(expectedItems.get(1))
                .thenReturn(expectedItems.get(2));

        List<Item> actualItems = csvReader.readFile();
        List<String> actualItemsStr = actualItems
                .stream()
                .map(item -> String.format("%s,%s,£%s", item.getProductCode(), item.getName(), item.getPrice()))
                .collect(Collectors.toList());

        assertThat(actualItemsStr).hasSameElementsAs(expectedItemsStr);
    }

    @Test
    public void invalidCSVFile_readIsInvoked_returnListOfRecords() throws AppException {
        String testDataFilePath = "src/test/resources/test.csv";
        Reader<Item> csvReader = new CSVReader<>(testDataFilePath, parser);

        expectedException.expect(AppException.class);
        expectedException.expectMessage("Failed to read file: " + testDataFilePath);

        csvReader.readFile();
    }

    private List<Item> buildItems() {
        List<Item> items = new ArrayList<>();
        List<String> productCodes = Arrays.asList("001", "002", "003");
        List<String> names = Arrays.asList("Travel Card Holder", "Personalised cuff links", "Kids T-shirt");
        List<Double> prices = Arrays.asList(9.25, 45.00, 19.95);

        for (int i = 0; i < productCodes.size(); i++) {
            Item item = Item
                    .builder()
                    .productCode(productCodes.get(i))
                    .name(names.get(i))
                    .price(prices.get(i))
                    .build();
            items.add(item);
        }
        return items;
    }
}
