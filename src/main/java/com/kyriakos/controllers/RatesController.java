package com.kyriakos.controllers;

import com.google.gson.Gson;
import com.kyriakos.models.internal.Currency;
import com.kyriakos.repositories.CurrencyRepository;
import com.kyriakos.services.CurrencyService;
import com.kyriakos.utils.ResultBuilder;
import com.kyriakos.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", consumes = "application/json")
public class RatesController {

    public static final Logger logger = LoggerFactory.getLogger(RatesController.class);

    @Autowired
    private CurrencyRepository currencyRepository;

    /* Get exchange rate from Currency A to Currency B. */
    @RequestMapping(value = "/exchange-rate", method = RequestMethod.POST)
    public ResponseEntity<String> exchangeRateConversion(@RequestBody Map<String, Object> payload) throws Exception {

        logger.info("Fetching rates");
        Gson gson = new Gson();
        Validator validator = new Validator();
        CurrencyService currencyService = new CurrencyService();


        /* Create a MD5Hash string regarding the incoming request */
        String MD5Hash = validator.createMD5Hash(payload.toString());
        /* Check if the incoming request is cached in order to fetch the result from Redis and not by the Service */
        Boolean isCached = validator.isCached(MD5Hash, currencyRepository);
        if (isCached) {

            Currency retrievedCurrency = currencyRepository.findById(MD5Hash).get();
            ResultBuilder resultBuilder = new ResultBuilder();

            String response = resultBuilder.buildResultCached(retrievedCurrency);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ArrayList<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("from");
        requiredParameters.add("to");

        /* Validate if all the required parameters exist. */
        ResponseEntity<String> validateParameters = validator.checkParameters(payload, requiredParameters);
        if (!(validateParameters.getStatusCode() == HttpStatus.OK)) {
            return validateParameters;
        }

        /* Validate if the incoming post body (payload) is in correct format regarding the internal Currency model. */
        ResponseEntity<String> validateModel = validator.checkModel(payload, gson);
        if (!(validateModel.getStatusCode() == HttpStatus.OK)) {
            return validateModel;
        }

        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(payload.toString(), Currency.class);
        String currencyFrom = currency.getFrom();
        String currencyTo = currency.getTo();

        /* Create a list with all the provided currency codes in order to validate it. */
        ArrayList<String> currencyCodes = new ArrayList<>();
        currencyCodes.add(currencyFrom);
        currencyCodes.add(currencyTo);

        /* Validate if all the provided currency codes are valid. */
        ResponseEntity<String> validateCurrencyCodes = validator.checkCurrencyCodes(currencyCodes);
        if (!(validateCurrencyCodes.getStatusCode() == HttpStatus.OK)) {
            return validateCurrencyCodes;
        }

        /* Return the response only if all validations are passed */
        String response = currencyService.getExchangeRateConversion(currencyFrom, currencyTo, MD5Hash, currencyRepository);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /* Get all exchange rates from Currency A. */
    @RequestMapping(value = "/exchange-rates", method = RequestMethod.POST)
    public ResponseEntity<String> exchangeRatesConversion(@RequestBody Map<String, Object> payload) throws Exception {

        logger.info("Fetching rates");
        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();
        Validator validator = new Validator();

        /* Create a MD5Hash string regarding the incoming request */
        String MD5Hash = validator.createMD5Hash(payload.toString());
        /* Check if the incoming request is cached in order to fetch the result from Redis and not by the Service */
        Boolean isCached = validator.isCached(MD5Hash, currencyRepository);
        if (isCached) {

            Currency retrievedCurrency = currencyRepository.findById(MD5Hash).get();
            ResultBuilder resultBuilder = new ResultBuilder();

            String response = resultBuilder.buildResultCached(retrievedCurrency);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ArrayList<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("base");

        /* Validate if the required parameter exist. */
        ResponseEntity<String> validateParameters = validator.checkParameters(payload, requiredParameters);
        if (!(validateParameters.getStatusCode() == HttpStatus.OK)) {
            return validateParameters;
        }

        /* Validate if the incoming post body (payload) is in correct format regarding the internal Currency model. */
        ResponseEntity<String> validateModel = validator.checkModel(payload, gson);
        if (!(validateModel.getStatusCode() == HttpStatus.OK)) {
            return validateModel;
        }

        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(payload.toString(), Currency.class);
        String currencyBase = currency.getBase();

        /* Create a list with all the provided currency codes in order to validate it. */
        ArrayList<String> currencyCodes = new ArrayList<>();
        currencyCodes.add(currencyBase);

        /* Validate if all the provided currency code is valid. */
        ResponseEntity<String> validateCurrencyCodes = validator.checkCurrencyCodes(currencyCodes);
        if (!(validateCurrencyCodes.getStatusCode() == HttpStatus.OK)) {
            return validateCurrencyCodes;
        }

        /* Return the response only if all validations are passed */
        String response = currencyService.getExchangeRatesConversion(currencyBase, MD5Hash, currencyRepository);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /* Get value conversion from Currency A to Currency B. */
    @RequestMapping(value = "/exchange-rate-value", method = RequestMethod.POST)
    public ResponseEntity<String> exchangeRateValue(@RequestBody Map<String, Object> payload) throws Exception {

        logger.info("Fetching rates");
        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();
        Validator validator = new Validator();

        /* Create a MD5Hash string regarding the incoming request */
        String MD5Hash = validator.createMD5Hash(payload.toString());
        /* Check if the incoming request is cached in order to fetch the result from Redis and not by the Service */
        Boolean isCached = validator.isCached(MD5Hash, currencyRepository);
        if (isCached) {

            Currency retrievedCurrency = currencyRepository.findById(MD5Hash).get();
            ResultBuilder resultBuilder = new ResultBuilder();

            String response = resultBuilder.buildResultCached(retrievedCurrency);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ArrayList<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("from");
        requiredParameters.add("to");
        requiredParameters.add("amount");

        /* Validate if all the required parameters exist. */
        ResponseEntity<String> validateParameters = validator.checkParameters(payload, requiredParameters);
        if (!(validateParameters.getStatusCode() == HttpStatus.OK)) {
            return validateParameters;
        }

        /* Validate if the incoming post body (payload) is in correct format regarding the internal Currency model. */
        ResponseEntity<String> validateModel = validator.checkModel(payload, gson);
        if (!(validateModel.getStatusCode() == HttpStatus.OK)) {
            return validateModel;
        }

        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(payload.toString(), Currency.class);
        String currencyFrom = currency.getFrom();
        String currencyTo = currency.getTo();

        /* Create a list with all the provided currency codes in order to validate it. */
        ArrayList<String> currencyCodes = new ArrayList<>();
        currencyCodes.add(currencyFrom);
        currencyCodes.add(currencyTo);

        /* Validate if all the provided currency codes are valid. */
        ResponseEntity<String> validateCurrencyCodes = validator.checkCurrencyCodes(currencyCodes);
        if (!(validateCurrencyCodes.getStatusCode() == HttpStatus.OK)) {
            return validateCurrencyCodes;
        }

        Float amount = currency.getAmount();

        /* Validate if the provided amount is valid. */
        ResponseEntity<String> validateAmount = validator.checkAmount(amount);
        if (!(validateAmount.getStatusCode() == HttpStatus.OK)) {
            return validateAmount;
        }

        /* Return the response only if all validations are passed */
        String response = currencyService.getExchangeRateValue(currencyFrom, currencyTo, amount, MD5Hash, currencyRepository);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /* Get value conversion from Currency A to a list of supplied currencies. */
    @RequestMapping(value = "/exchange-rates-value", method = RequestMethod.POST)
    public ResponseEntity<String> exchangeRatesValue(@RequestBody Map<String, Object> payload) throws Exception {

        logger.info("Fetching rates");
        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();
        Validator validator = new Validator();

        /* Create a MD5Hash string regarding the incoming request */
        String MD5Hash = validator.createMD5Hash(payload.toString());
        /* Check if the incoming request is cached in order to fetch the result from Redis and not by the Service */
        Boolean isCached = validator.isCached(MD5Hash, currencyRepository);
        if (isCached) {

            Currency retrievedCurrency = currencyRepository.findById(MD5Hash).get();
            ResultBuilder resultBuilder = new ResultBuilder();

            String response = resultBuilder.buildResultCached(retrievedCurrency);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ArrayList<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("amount");
        requiredParameters.add("base");
        requiredParameters.add("symbols");

        /* Validate if all the required parameters exist. */
        ResponseEntity<String> validateParameters = validator.checkParameters(payload, requiredParameters);
        if (!(validateParameters.getStatusCode() == HttpStatus.OK)) {
            return validateParameters;
        }

        /* Validate if the incoming post body (payload) is in correct format regarding the internal Currency model. */
        ResponseEntity<String> validateModel = validator.checkModel(payload, gson);
        if (!(validateModel.getStatusCode() == HttpStatus.OK)) {
            return validateModel;
        }

        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(payload.toString(), Currency.class);
        String base = currency.getBase();
        List<String> symbols = currency.getSymbols();

        /* Create a list with all the provided currency codes in order to validate it. */
        ArrayList<String> currencyCodes = new ArrayList<>();
        currencyCodes.add(base);
        Iterator<String> it = symbols.iterator();
        while (it.hasNext()) {
            String value = it.next();
            currencyCodes.add(value);
        }

        /* Validate if all the provided currency codes are valid. */
        ResponseEntity<String> validateCurrencyCodes = validator.checkCurrencyCodes(currencyCodes);
        if (!(validateCurrencyCodes.getStatusCode() == HttpStatus.OK)) {
            return validateCurrencyCodes;
        }

        Float amount = currency.getAmount();

        /* Validate if the provided amount is valid. */
        ResponseEntity<String> validateAmount = validator.checkAmount(amount);
        if (!(validateAmount.getStatusCode() == HttpStatus.OK)) {
            return validateAmount;
        }

        /* Return the response only if all validations are passed */
        String response = currencyService.getExchangeRatesValue(base, amount, symbols, MD5Hash, currencyRepository);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
