package com.kyriakos.services;

import com.google.gson.Gson;
import com.kyriakos.models.internal.Currency;
import com.kyriakos.repositories.CurrencyRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
public class TestCurrencyService {

    @MockBean
    CurrencyRepository currencyRepository;

    /* Test getExchangeRateConversion method. */
    @Test
    public void testGetExchangeRateConversion() throws IOException {

        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();
        String currencyFrom = "EUR";
        String currencyTo = "USD";
        String MD5Hash = "a656a481f72e79066adb842131855306";
        Float amount = Float.valueOf(1);

        String response = currencyService.getExchangeRateConversion(currencyFrom, currencyTo, MD5Hash, currencyRepository);
        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(response, Currency.class);
        /* Check if the response contains the proper parameters */
        Assert.assertNotNull(currency.getFrom());
        Assert.assertNotNull(currency.getTo());
        Assert.assertNotNull(currency.getAmount());
        Assert.assertNotNull(currency.getResult());
        Assert.assertNotNull(currency.getDate());

        Assert.assertNull(currency.getBase());
        Assert.assertNull(currency.getRates());
        Assert.assertNull(currency.getSymbols());
        Assert.assertNull(currency.getId());

        /* Check the amount value */
        Assert.assertEquals(currency.getAmount(), amount);

        /* Check if the date is today's date  */
        Assert.assertTrue(checkDate(currency.getDate()));

    }

    /* Test getExchangeRatesConversion method. */
    @Test
    public void testGetExchangeRatesConversion() throws IOException {

        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();
        String base = "EUR";
        String MD5Hash = "a656a481f72e79066adb842131855307";

        String response = currencyService.getExchangeRatesConversion(base, MD5Hash, currencyRepository);
        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(response, Currency.class);
        /* Check if the response contains the proper parameters */
        Assert.assertNotNull(currency.getBase());
        Assert.assertNotNull(currency.getRates());
        Assert.assertNotNull(currency.getDate());

        Assert.assertNull(currency.getFrom());
        Assert.assertNull(currency.getTo());
        Assert.assertNull(currency.getAmount());
        Assert.assertNull(currency.getResult());
        Assert.assertNull(currency.getSymbols());
        Assert.assertNull(currency.getId());

        /* Check if the date is today's date  */
        Assert.assertTrue(checkDate(currency.getDate()));
    }


    /* Test getExchangeRateValue method. */
    @Test
    public void testGetExchangeRateValue() throws IOException {

        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();
        String currencyFrom = "EUR";
        String currencyTo = "USD";
        Float amount = Float.valueOf(15);
        String MD5Hash = "a656a481f72e79066adb842131855307";

        String response = currencyService.getExchangeRateValue(currencyFrom, currencyTo, amount, MD5Hash, currencyRepository);
        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(response, Currency.class);
        /* Check if the response contains the proper parameters */
        Assert.assertNotNull(currency.getFrom());
        Assert.assertNotNull(currency.getTo());
        Assert.assertNotNull(currency.getAmount());
        Assert.assertNotNull(currency.getResult());
        Assert.assertNotNull(currency.getDate());

        Assert.assertNull(currency.getBase());
        Assert.assertNull(currency.getRates());
        Assert.assertNull(currency.getSymbols());
        Assert.assertNull(currency.getId());

        /* Check the amount value */
        Assert.assertEquals(currency.getAmount(), amount);

        /* Check if the date is today's date  */
        Assert.assertTrue(checkDate(currency.getDate()));
    }


    /* Test getExchangeRatesValue method. */
    @Test
    public void testGetExchangeRatesValue() throws IOException {

        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();
        ArrayList<String> symbols = new ArrayList<String>();
        symbols.add("EUR");
        symbols.add("GBP");
        String base = "USD";
        Float amount = Float.valueOf(7);
        String MD5Hash = "a656a481f72e79066adb842131855307";

        String response = currencyService.getExchangeRatesValue(base, amount, symbols, MD5Hash, currencyRepository);
        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(response, Currency.class);
        /* Check if the response contains the proper parameters */
        Assert.assertNotNull(currency.getAmount());
        Assert.assertNotNull(currency.getDate());
        Assert.assertNotNull(currency.getBase());
        Assert.assertNotNull(currency.getRates());

        Assert.assertNull(currency.getFrom());
        Assert.assertNull(currency.getTo());
        Assert.assertNull(currency.getSymbols());
        Assert.assertNull(currency.getId());
        Assert.assertNull(currency.getResult());
        /* Check the amount value */
        Assert.assertEquals(currency.getAmount(), amount);

        /* Check if the date is today's date  */
        Assert.assertTrue(checkDate(currency.getDate()));
    }


    public Boolean checkDate(String date) throws IOException {

        long millis = System.currentTimeMillis();
        java.sql.Date currentDate = new java.sql.Date(millis);
        return date.equalsIgnoreCase(currentDate.toString());
    }


}
