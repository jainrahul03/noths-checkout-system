package com.example;

import com.example.checkout.Checkout;
import com.example.exception.AppException;
import com.example.item.Item;
import com.example.item.repository.ItemRepository;
import com.example.item.repository.ItemRepositoryImpl;
import com.example.promotions.PromotionalRules;
import com.example.promotions.PromotionsInitializer;
import com.example.promotions.repository.PromotionsRepository;
import com.example.promotions.repository.PromotionsRepositoryImpl;
import com.example.reader.parser.ItemParser;
import com.example.reader.parser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entry point of the entire checkout-system application.
 */
public class CheckoutSystemApp {
    public static void main(String[] args) throws AppException {
        //read the 'items.csv' file from the given path and load items into ItemRepository
        String itemsCSVFilePath = "src/main/resources/items.csv";
        Parser<Item> itemParser = new ItemParser();
        ItemRepository itemRepository = new ItemRepositoryImpl(itemsCSVFilePath, itemParser);

        //Create the PromotionsRepository
        PromotionsRepository promotionsRepository = new PromotionsRepositoryImpl();
        //Define and load promotions into PromotionsRepository
        PromotionsInitializer.initializePromotions(itemRepository, promotionsRepository);

        //Expect Command Line Arguments to contain product codes of items in basket.
        if (args.length == 0) {
            throw new AppException("Please enter the basket items");
        }

        //Validate the input product codes to check if valid product codes are provided
        String[] basketItems = args[0].split(",");
        List<Item> itemsToScan = new ArrayList<>();
        for (String productCode : basketItems) {
            Item item = itemRepository.getItemByProductCode(productCode);
            if (Objects.isNull(item)) {
                throw new AppException("Invalid product code: " + productCode);
            }
            itemsToScan.add(item);
        }

        //Create an instance of PromotionalRules
        PromotionalRules promotionalRules = new PromotionalRules(promotionsRepository);

        //Create an instance of Checkout (Basket) and scan all input product codes
        Checkout checkout = new Checkout(promotionalRules);
        for (Item item : itemsToScan) {
            checkout.scan(item);
        }

        //Display the basket total with all promotion discounts applied.
        System.out.println("Checkout Total: £" + checkout.getTotal());
    }
}
