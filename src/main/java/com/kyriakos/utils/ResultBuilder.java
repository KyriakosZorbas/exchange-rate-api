package com.kyriakos.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

}
