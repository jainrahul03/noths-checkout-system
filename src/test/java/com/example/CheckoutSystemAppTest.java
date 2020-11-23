package com.example;

import com.example.exception.AppException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CheckoutSystemAppTest {

    private String[] args;

    @Rule public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        args = new String[0];
    }

    @Test
    public void noBasketItems_appIsInvoked_printErrorMessageAndExitApp() throws AppException {
        expectedException.expect(AppException.class);
        expectedException.expectMessage("Please enter the basket items");

        CheckoutSystemApp.main(args);
    }

    @Test
    public void invalidBasketItem_appIsInvoked_printErrorMessageAndExitApp() throws AppException {
        args = new String[1];
        args[0] = "001,002,003,004";
        expectedException.expect(AppException.class);
        expectedException.expectMessage("Invalid product code: 004");

        CheckoutSystemApp.main(args);
    }

    @Test
    public void validBasketItems_appIsInvoked_printCheckoutTotal() throws AppException {
        args = new String[1];
        args[0] = "001,002,003";

        CheckoutSystemApp.main(args);
    }
}
