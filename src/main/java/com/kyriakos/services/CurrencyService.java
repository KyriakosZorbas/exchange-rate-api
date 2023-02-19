package com.kyriakos.services;

import com.google.gson.Gson;
import com.kyriakos.models.external.Currency;
import com.kyriakos.models.external.Rates;
import com.kyriakos.repositories.CurrencyRepository;
import com.kyriakos.utils.ResultBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CurrencyService {

    private static final String URL = "https://api.exchangerate.host/";

    public String getExchangeRateConversion(String currencyFrom, String currencyTo, String MD5Hash, CurrencyRepository currencyRepository) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url(URL + "convert?from=" + currencyFrom + "&to=" + currencyTo)
                .build();

        Response response = client.newCall(request).execute();

        String myResult = response.body().string();

        Gson gson = new Gson();
        Currency currency = gson.fromJson(myResult, Currency.class);

        Float amount = currency.getQuery().getAmount();
        String date = currency.getDate();
        Double resultAfterConversion = currency.getResult();

        ResultBuilder rb = new ResultBuilder();

        String result = rb.buildResultForCurrencyConversion(currencyFrom, currencyTo, amount, resultAfterConversion, date, MD5Hash, currencyRepository);

        return result;

    }


    public String getExchangeRatesConversion(String base, String MD5Hash, CurrencyRepository currencyRepository) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url(URL + "latest?base=" + base)
                .build();
        Response response = client.newCall(request).execute();

        String myResult = response.body().string();

        Gson gson = new Gson();
        Currency currency = gson.fromJson(myResult, Currency.class);

        String date = currency.getDate();
        Rates rates = currency.getRates();

        ResultBuilder resultBuilder = new ResultBuilder();

        String result = resultBuilder.buildResultForCurrencyRates(base, date, rates, MD5Hash, currencyRepository);

        return result;

    }


    public String getExchangeRateValue(String currencyFrom, String currencyTo, Float amount, String MD5Hash, CurrencyRepository currencyRepository) throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url(URL + "convert?from=" + currencyFrom + "&to=" + currencyTo + "&amount=" + amount)
                .build();
        Response response = client.newCall(request).execute();

        String myResult = response.body().string();

        Gson gson = new Gson();
        Currency currency = gson.fromJson(myResult, Currency.class);

        String date = currency.getDate();
        Double resultAfterConversion = currency.getResult();

        ResultBuilder rb = new ResultBuilder();

        String result = rb.buildResultForCurrencyConversion(currencyFrom, currencyTo, amount, resultAfterConversion, date, MD5Hash, currencyRepository);

        return result;

    }

    public String getExchangeRatesValue(String base, Float amount, List<String> symbols, String MD5Hash, CurrencyRepository currencyRepository) throws IOException {


        String symbolsCommaSeparated = String.join(",", symbols);

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(URL + "latest?base=" + base + "&amount=" + amount + "&symbols=" + symbolsCommaSeparated)
                .build();
        Response response = client.newCall(request).execute();

        String myResult = response.body().string();

        Gson gson = new Gson();
        Currency currency = gson.fromJson(myResult, Currency.class);

        String date = currency.getDate();
        Rates rates = currency.getRates();

        ResultBuilder rb = new ResultBuilder();

        String result = rb.buildResultForValueConversionForMultipleCurrency(amount, base, date, rates, MD5Hash, currencyRepository);

        return result;

    }

}
