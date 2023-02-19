package com.kyriakos.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kyriakos.models.external.Rates;
import com.kyriakos.models.internal.Currency;

public class ResultBuilder {

    public String buildResultForCurrencyConversion(String currencyFrom, String currencyTo, Float amount, Double resultAfterConversion, String date) {

        Currency currency = new Currency();

        currency.setFrom(currencyFrom);
        currency.setTo(currencyTo);
        currency.setAmount(amount);
        currency.setResult(resultAfterConversion);
        currency.setDate(date);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(currency);

        return json;
    }

    public String buildResultForCurrencyRates(String base, String date, Rates rates) {

        Currency currency = new Currency();
        Gson gson = new Gson();

        currency.setBase(base);
        currency.setDate(date);

        /* Migrate Rates from external model to the internal model */
        String jsonString = new Gson().toJson(rates);
        currency.setRates(gson.fromJson(jsonString, com.kyriakos.models.internal.Rates.class));

        gson = new GsonBuilder().create();
        String json = gson.toJson(currency);

        return json;
    }
}
