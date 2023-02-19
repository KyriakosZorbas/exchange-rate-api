package com.kyriakos.services;

import com.google.gson.Gson;
import com.kyriakos.models.external.Currency;
import com.kyriakos.utils.ResultBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CurrencyService {

    private static final String URL = "https://api.exchangerate.host/";

    public String getExchangeRateConversion(String currencyFrom, String currencyTo) throws IOException {

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

        String result = rb.buildResultForCurrencyConversion(currencyFrom, currencyTo, amount, resultAfterConversion, date);

        return result;

    }

}
