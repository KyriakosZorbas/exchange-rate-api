package com.kyriakos.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RatesController.class)
public class TestRatesController {

    String ExchangeRate = "/api/exchange-rate";
    String ExchangeRates = "/api/exchange-rates";
    String ExchangeRateValue = "/api/exchange-rate-value";
    String ExchangeRatesValue = "/api/exchange-rates-value";
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;


    /* TEST Get exchange rate from Currency A to Currency B. */
    /* Endpoint /api/exchange-rate */
    @Test
    public void testGetExchangeRateFromCurrencyAtoCurrencyB() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        /* Sent request with proper parameters and values. */
        String postBody = "{\"from\":\"USD\",\"to\":\"EUR\"}";
        testNormalBehavior(postBody, ExchangeRate);

        /* Sent request with a missing parameter, in this case without the "from" parameter. */
        postBody = "{\"to\":\"EUR\"}";
        testMissingParametersException(postBody, ExchangeRate);

        /* Sent request with wrong format, in this case the "from" parameter instead of string will be a json array. */
        postBody = "{\"from\":[\"USD\"],\"to\":\"EUR\"}";
        testInternalModel(postBody, ExchangeRate);

        /* Sent request with invalid currency code, in this case the "from" parameter will have as a currency code the value "XXX". */
        postBody = "{\"from\":\"XXX\",\"to\":\"EUR\"}";
        testCurrencyCodes(postBody, ExchangeRate);

    }


    /* TEST Get all exchange rates from Currency A. */
    /* Endpoint /api/exchange-rates */
    @Test
    public void testGetAllExchangeRatesFromCurrencyA() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        /* Sent request with proper parameters and values. */
        String postBody = "{\"base\":\"USD\"}";
        testNormalBehavior(postBody, ExchangeRates);

        /* Sent request with a missing parameter, in this case without the "base" parameter. */
        postBody = "{\"something\":\"EUR\"}";
        testMissingParametersException(postBody, ExchangeRates);

        /* Sent request with wrong format, in this case the "base" parameter instead of string will be a json array. */
        postBody = "{\"base\":[\"USD\"]}";
        testInternalModel(postBody, ExchangeRates);

        /* Sent request with invalid currency code, in this case the "base" parameter will have as a currency code the value "xxxx". */
        postBody = "{\"base\":\"xxxx\"}";
        testCurrencyCodes(postBody, ExchangeRates);


    }


    /* TEST Get value conversion from Currency A to Currency B. */
    /* Endpoint /api/exchange-rate-value */
    @Test
    public void testGetValueConversionFromCurrencyAtoCurrencyB() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        /* Sent request with proper parameters and values. */
        String postBody = "{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":12.56}";
        testNormalBehavior(postBody, ExchangeRateValue);

        /* Sent request with a missing parameter, in this case without the "to" parameter. */
        postBody = "{\"from\":\"USD\",\"amount\":12.56}";
        testMissingParametersException(postBody, ExchangeRateValue);

        /* Sent request with wrong format, in this case the "amount" parameter would be a string instead of number. */
        postBody = "{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":\"string\"}";
        testInternalModel(postBody, ExchangeRateValue);

        /* Sent request with invalid currency code, in this case the "to" parameter will have as a currency code the value "xxxxxx". */
        postBody = "{\"from\":\"USD\",\"to\":\"xxxxxx\",\"amount\":12.56}";
        testCurrencyCodes(postBody, ExchangeRateValue);


        /* Sent request with invalid amount, in this case the "amount" parameter would be a negative number. */
        postBody = "{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":-12.56}";
        testAmount(postBody, ExchangeRateValue);

        /* Sent request with invalid amount, in this case the "amount" parameter would be a very large number. */
        postBody = "{\"from\":\"USD\",\"to\":\"EUR\",\"amount\":1000000000000000000000000000000000000000000000000000000000000000}";
        testAmount(postBody, ExchangeRateValue);

    }

    /* TEST Get value conversion from Currency A to a list of supplied currencies. */
    /* Endpoint /api/exchange-rates-value */
    @Test
    public void testGetValueConversionFromCurrencyAtoAlistOfSuppliedCurrencies() throws Exception {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        /* Sent request with proper parameters and values. */
        String postBody = "{\"base\":\"USD\",\"amount\":2.0,\"symbols\":[\"EUR\",\"GBP\"]}";
        testNormalBehavior(postBody, ExchangeRatesValue);

        /* Sent request with a missing parameter, in this case without the "symbols" parameter. */
        postBody = "{\"base\":\"USD\",\"amount\":12.56}";
        testMissingParametersException(postBody, ExchangeRatesValue);

        /* Sent request with wrong format, in this case the "symbols" parameter would be a number instead of array. */
        postBody = "{\"base\":\"USD\",\"amount\":2.0,\"symbols\":5}";
        testInternalModel(postBody, ExchangeRatesValue);

        /* Sent request with invalid currency code, in this case the "base" parameter will have as a currency code the value "x". */
        postBody = "{\"base\":\"x\",\"amount\":2.0,\"symbols\":[\"EUR\",\"GBP\"]}";
        testCurrencyCodes(postBody, ExchangeRatesValue);

        /* Sent request with invalid currency code, in this case the "symbols" parameter will contain as a currency code the value "xx". */
        postBody = "{\"base\":\"x\",\"amount\":2.0,\"symbols\":[\"xx\",\"GBP\"]}";
        testCurrencyCodes(postBody, ExchangeRatesValue);

        /* Sent request with invalid amount, in this case the "amount" parameter would be a negative number. */
        postBody = "{\"base\":\"USD\",\"amount\":-2.0,\"symbols\":[\"EUR\",\"GBP\"]}";
        testAmount(postBody, ExchangeRatesValue);

        /* Sent request with invalid amount, in this case the "amount" parameter would be a very large number. */
        postBody = "{\"base\":\"USD\",\"amount\":1000000000000000000000000000000000000000000000000000000000000000,\"symbols\":[\"EUR\",\"GBP\"]}";
        testAmount(postBody, ExchangeRatesValue);

    }

    public void testNormalBehavior(String postBody, String endpoint) throws Exception {

        this.mockMvc.perform(post(endpoint)
                        .content(postBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public void testMissingParametersException(String postBody, String endpoint) throws Exception {

        this.mockMvc.perform(post(endpoint)
                        .content(postBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    public void testInternalModel(String postBody, String endpoint) throws Exception {

        this.mockMvc.perform(post(endpoint)
                        .content(postBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    public void testCurrencyCodes(String postBody, String endpoint) throws Exception {

        this.mockMvc.perform(post(endpoint)
                        .content(postBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    public void testAmount(String postBody, String endpoint) throws Exception {

        this.mockMvc.perform(post(endpoint)
                        .content(postBody)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}


