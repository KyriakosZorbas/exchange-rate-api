package com.kyriakos.controllers;

import com.google.gson.Gson;
import com.kyriakos.models.internal.Currency;
import com.kyriakos.services.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/api", consumes = "application/json")
public class RatesController {

    public static final Logger logger = LoggerFactory.getLogger(RatesController.class);


    /* Get exchange rate from Currency A to Currency B. */
    @RequestMapping(value = "/exchange-rate", method = RequestMethod.POST)
    public ResponseEntity<String> exchangeRateConversion(@RequestBody Map<String, Object> payload) throws Exception {

        logger.info("Fetching rates");
        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();

        ArrayList<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("from");
        requiredParameters.add("to");

        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(payload.toString(), Currency.class);
        String currencyFrom = currency.getFrom();
        String currencyTo = currency.getTo();

        /* Create a list with all the provided currency codes in order to validate it. */
        ArrayList<String> currencyCodes = new ArrayList<>();
        currencyCodes.add(currencyFrom);
        currencyCodes.add(currencyTo);

        String response = currencyService.getExchangeRateConversion(currencyFrom, currencyTo);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /* Get all exchange rates from Currency A. */
    @RequestMapping(value = "/exchange-rates", method = RequestMethod.POST)
    public ResponseEntity<String> exchangeRatesConversion(@RequestBody Map<String, Object> payload) throws Exception {

        logger.info("Fetching rates");
        Gson gson = new Gson();
        CurrencyService currencyService = new CurrencyService();

        ArrayList<String> requiredParameters = new ArrayList<>();
        requiredParameters.add("base");


        /* Parse the incoming post body (payload) to the internal Currency model. */
        Currency currency = gson.fromJson(payload.toString(), Currency.class);
        String currencyBase = currency.getBase();

        /* Create a list with all the provided currency codes in order to validate it. */
        ArrayList<String> currencyCodes = new ArrayList<>();
        currencyCodes.add(currencyBase);

        /* Return the response only if all validations are passed */
        String response = currencyService.getExchangeRatesConversion(currencyBase);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
