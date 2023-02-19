package com.kyriakos.utils;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TestValidator {

    /* Test isCurrencyCodeValid method by giving as input right and wrong Currency code. */
    @Test
    public void testIsCurrencyCodeValid() {

        Validator validator = new Validator();

        String currencyCode = "EUR";
        Boolean isCurrencyCodeValid = validator.isCurrencyCodeValid(currencyCode);
        Assert.assertTrue(isCurrencyCodeValid);

        currencyCode = "XXXX";
        isCurrencyCodeValid = validator.isCurrencyCodeValid(currencyCode);
        Assert.assertFalse(isCurrencyCodeValid);
    }


    /* Test checkParameters method by giving as input right and wrong parameters. */
    @Test
    public void testCheckParameters() {

        Validator validator = new Validator();


        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("from", "EUR");
        payload.put("to", "USD");

        ArrayList<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("from");
        requiredParameters.add("to");

        ResponseEntity<String> validateParameters = validator.checkParameters(payload, requiredParameters);
        Assert.assertEquals(validateParameters.getStatusCode(), HttpStatus.OK);

        /* Clear the array in order to add wrong values */
        requiredParameters.clear();
        requiredParameters.add("WrongParameter");

        validateParameters = validator.checkParameters(payload, requiredParameters);
        Assert.assertEquals(validateParameters.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);

    }


    /* Test checkCurrencyCodes method by giving as input right and wrong Currency codes. */
    @Test
    public void testCheckCurrencyCodes() {

        Validator validator = new Validator();

        ArrayList<String> currencyCodes = new ArrayList<>();
        currencyCodes.add("USD");
        currencyCodes.add("EUR");

        ResponseEntity<String> validateCurrencyCodes = validator.checkCurrencyCodes(currencyCodes);
        Assert.assertEquals(validateCurrencyCodes.getStatusCode(), HttpStatus.OK);

        /* Clear the array in order to add wrong values */
        currencyCodes.clear();
        currencyCodes.add("USD");
        currencyCodes.add("COIN");
        currencyCodes.add("EUR");

        validateCurrencyCodes = validator.checkCurrencyCodes(currencyCodes);
        Assert.assertEquals(validateCurrencyCodes.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    /* Test checkAmount method by giving as input right and wrong value for the currency amount. */
    @Test
    public void testCheckAmount() {

        Validator validator = new Validator();

        Float amount = Float.valueOf(1);
        ResponseEntity<String> validateAmount = validator.checkAmount(amount);
        Assert.assertEquals(validateAmount.getStatusCode(), HttpStatus.OK);

        /* Sent negative amount */
        amount = Float.valueOf(-1);
        validateAmount = validator.checkAmount(amount);
        Assert.assertEquals(validateAmount.getStatusCode(), HttpStatus.BAD_REQUEST);

    }

    /* Test checkModel method by giving as input right and wrong model regarding the internal Currency model. */
    @Test
    public void testCheckModel() {

        Validator validator = new Validator();
        Gson gson = new Gson();

        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("from", "EUR");
        payload.put("to", "USD");

        ResponseEntity<String> validateModel = validator.checkModel(payload, gson);
        Assert.assertEquals(validateModel.getStatusCode(), HttpStatus.OK);

        /* Sent null in order to check if returns error */
        payload = null;
        validateModel = validator.checkModel(payload, gson);
        Assert.assertEquals(validateModel.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /* Test CreateMD5Hash method by giving as input different strings */
    @Test
    public void testCreateMD5Hash() throws NoSuchAlgorithmException {

        Validator validator = new Validator();

        String input = "{\"from\":\"EUR\",\"to\":\"GBP\"}";
        String MD5Hash = validator.createMD5Hash(input);
        Assert.assertEquals(MD5Hash, "eaa60ecc09eb2fb6cb7e191f68bdda2f");

        /* Change the input to check if the MD5Hash string will change. */
        input = "{\"from\":\"EUR\",\"to\":\"USD\"}";
        MD5Hash = validator.createMD5Hash(input);
        Assert.assertNotEquals(MD5Hash, "eaa60ecc09eb2fb6cb7e191f68bdda2f");

    }


}
