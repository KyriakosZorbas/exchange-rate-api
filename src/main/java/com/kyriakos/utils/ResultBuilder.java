package com.kyriakos.utils;


import com.google.gson.*;
import com.kyriakos.models.external.Rates;
import com.kyriakos.models.internal.Currency;
import com.kyriakos.repositories.CurrencyRepository;

public class ResultBuilder {

    public String buildResultForCurrencyConversion(String currencyFrom, String currencyTo, Float amount, Double resultAfterConversion, String date,String MD5Hash, CurrencyRepository currencyRepository) {

        Currency currency = new Currency();

        currency.setFrom(currencyFrom);
        currency.setTo(currencyTo);
        currency.setAmount(amount);
        currency.setResult(resultAfterConversion);
        currency.setDate(date);

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(currency);

        currency.setId(MD5Hash);
        currencyRepository.save(currency);

        return json;
    }
    public String buildResultCached(Currency retrievedCurrency) {

        Gson gson = new GsonBuilder().create();

        /* Remove the id from the response */
        JsonElement jsonElement = new JsonParser().parse(gson.toJson(retrievedCurrency));
        jsonElement.getAsJsonObject().remove("id");

        String json = gson.toJson(jsonElement);

        return json;
    }

    public String buildResultForCurrencyRates(String base, String date, Rates rates,String MD5Hash, CurrencyRepository currencyRepository) {

        Currency currency = new Currency();
        Gson gson = new Gson();

        currency.setBase(base);
        currency.setDate(date);

        /* Migrate Rates from external model to the internal model */
        String jsonString = new Gson().toJson(rates);
        currency.setRates(gson.fromJson(jsonString, com.kyriakos.models.internal.Rates.class));

        gson = new GsonBuilder().create();
        String json = gson.toJson(currency);

        currency.setId(MD5Hash);
        currencyRepository.save(currency);

        return json;
    }


    public String buildResultForValueConversionForMultipleCurrency(Float amount, String base, String date, Rates rates,String MD5Hash, CurrencyRepository currencyRepository) {

        Currency currency = new Currency();
        Gson gson = new Gson();

        currency.setBase(base);
        currency.setDate(date);
        currency.setAmount(amount);

        /* Migrate Rates from external model to the internal model */
        String jsonString = new Gson().toJson(rates);
        currency.setRates(gson.fromJson(jsonString, com.kyriakos.models.internal.Rates.class));

        gson = new GsonBuilder().create();
        String json = gson.toJson(currency);

        currency.setId(MD5Hash);
        currencyRepository.save(currency);

        return json;
    }


}
