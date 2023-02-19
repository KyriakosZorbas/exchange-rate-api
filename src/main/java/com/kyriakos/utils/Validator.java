package com.kyriakos.utils;

import com.google.gson.Gson;
import com.kyriakos.exceptions.ExchangeRateApplicationException;
import com.kyriakos.models.internal.Currency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Service
public class Validator {

    ArrayList<String> currencies = new ArrayList<String>();

    public boolean isCurrencyCodeValid(String currencyCode) {

        /* Create a list with all the available currency codes */
        populateCurrencies();

        return currencies.contains(currencyCode);
    }

    public void populateCurrencies() {
        currencies.add("AED");
        currencies.add("AFN");
        currencies.add("ALL");
        currencies.add("AMD");
        currencies.add("ANG");
        currencies.add("AOA");
        currencies.add("ARS");
        currencies.add("AUD");
        currencies.add("AWG");
        currencies.add("AZN");
        currencies.add("BAM");
        currencies.add("BBD");
        currencies.add("BDT");
        currencies.add("BGN");
        currencies.add("BHD");
        currencies.add("BIF");
        currencies.add("BMD");
        currencies.add("BND");
        currencies.add("BOB");
        currencies.add("BRL");
        currencies.add("BSD");
        currencies.add("BTC");
        currencies.add("BTN");
        currencies.add("BWP");
        currencies.add("BYN");
        currencies.add("BZD");
        currencies.add("CAD");
        currencies.add("CDF");
        currencies.add("CHF");
        currencies.add("CLF");
        currencies.add("CLP");
        currencies.add("CNH");
        currencies.add("CNY");
        currencies.add("COP");
        currencies.add("CRC");
        currencies.add("CUC");
        currencies.add("CUP");
        currencies.add("CVE");
        currencies.add("CZK");
        currencies.add("DJF");
        currencies.add("DKK");
        currencies.add("DOP");
        currencies.add("DZD");
        currencies.add("EGP");
        currencies.add("ERN");
        currencies.add("ETB");
        currencies.add("EUR");
        currencies.add("FJD");
        currencies.add("FKP");
        currencies.add("GBP");
        currencies.add("GEL");
        currencies.add("GGP");
        currencies.add("GHS");
        currencies.add("GIP");
        currencies.add("GMD");
        currencies.add("GNF");
        currencies.add("GTQ");
        currencies.add("GYD");
        currencies.add("HKD");
        currencies.add("HNL");
        currencies.add("HRK");
        currencies.add("HTG");
        currencies.add("HUF");
        currencies.add("IDR");
        currencies.add("ILS");
        currencies.add("IMP");
        currencies.add("INR");
        currencies.add("IQD");
        currencies.add("IRR");
        currencies.add("ISK");
        currencies.add("JEP");
        currencies.add("JMD");
        currencies.add("JOD");
        currencies.add("JPY");
        currencies.add("KES");
        currencies.add("KGS");
        currencies.add("KHR");
        currencies.add("KMF");
        currencies.add("KPW");
        currencies.add("KRW");
        currencies.add("KWD");
        currencies.add("KYD");
        currencies.add("KZT");
        currencies.add("LAK");
        currencies.add("LBP");
        currencies.add("LKR");
        currencies.add("LRD");
        currencies.add("LSL");
        currencies.add("LYD");
        currencies.add("MAD");
        currencies.add("MDL");
        currencies.add("MGA");
        currencies.add("MKD");
        currencies.add("MMK");
        currencies.add("MNT");
        currencies.add("MOP");
        currencies.add("MRU");
        currencies.add("MUR");
        currencies.add("MVR");
        currencies.add("MWK");
        currencies.add("MXN");
        currencies.add("MYR");
        currencies.add("MZN");
        currencies.add("NAD");
        currencies.add("NGN");
        currencies.add("NIO");
        currencies.add("NOK");
        currencies.add("NPR");
        currencies.add("NZD");
        currencies.add("OMR");
        currencies.add("PAB");
        currencies.add("PEN");
        currencies.add("PGK");
        currencies.add("PHP");
        currencies.add("PKR");
        currencies.add("PLN");
        currencies.add("PYG");
        currencies.add("QAR");
        currencies.add("RON");
        currencies.add("RSD");
        currencies.add("RUB");
        currencies.add("RWF");
        currencies.add("SAR");
        currencies.add("SBD");
        currencies.add("SCR");
        currencies.add("SDG");
        currencies.add("SEK");
        currencies.add("SGD");
        currencies.add("SHP");
        currencies.add("SLL");
        currencies.add("SOS");
        currencies.add("SRD");
        currencies.add("SSP");
        currencies.add("STD");
        currencies.add("STN");
        currencies.add("SVC");
        currencies.add("SYP");
        currencies.add("SZL");
        currencies.add("THB");
        currencies.add("TJS");
        currencies.add("TMT");
        currencies.add("TND");
        currencies.add("TOP");
        currencies.add("TRY");
        currencies.add("TTD");
        currencies.add("TWD");
        currencies.add("TZS");
        currencies.add("UAH");
        currencies.add("UGX");
        currencies.add("USD");
        currencies.add("UYU");
        currencies.add("UZS");
        currencies.add("VES");
        currencies.add("VND");
        currencies.add("VUV");
        currencies.add("WST");
        currencies.add("XAF");
        currencies.add("XAG");
        currencies.add("XAU");
        currencies.add("XCD");
        currencies.add("XDR");
        currencies.add("XOF");
        currencies.add("XPD");
        currencies.add("XPF");
        currencies.add("XPT");
        currencies.add("YER");
        currencies.add("ZAR");
        currencies.add("ZMW");
        currencies.add("ZWL");
    }

    public ResponseEntity<String> checkParameters(Map<String, Object> payload, ArrayList<String> requiredParameters) {

        ExchangeRateApplicationException exchangeRateApplicationException = new ExchangeRateApplicationException();

        Iterator<String> it = requiredParameters.iterator();
        while (it.hasNext()) {
            String parameter = it.next();
            if (!payload.containsKey(parameter)) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                        .body(exchangeRateApplicationException.missingParameterExceptionTextMessage(parameter));
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("OK");
    }

    public ResponseEntity<String> checkCurrencyCodes(ArrayList<String> currencyCodes) {

        ExchangeRateApplicationException exchangeRateApplicationException = new ExchangeRateApplicationException();

        Iterator<String> it = currencyCodes.iterator();
        while (it.hasNext()) {
            String value = it.next();
            if (!isCurrencyCodeValid(value)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(exchangeRateApplicationException.getErrorForInvalidCurrencyCodeTextMessage(value));
            }
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("OK");
    }

    public ResponseEntity<String> checkAmount(Float amount) {

        ExchangeRateApplicationException exchangeRateApplicationException = new ExchangeRateApplicationException();

        if (amount.isInfinite()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exchangeRateApplicationException.getErrorForAmountTooLargeTextMessage(amount));
        } else if (amount < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(exchangeRateApplicationException.getErrorForInvalidAmountTextMessage(amount));
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body("OK");
    }

    public ResponseEntity<String> checkModel(Map<String, Object> payload, Gson gson) {

        ExchangeRateApplicationException exchangeRateApplicationException = new ExchangeRateApplicationException();

        try {
            gson.fromJson(payload.toString(), Currency.class);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exchangeRateApplicationException.wrongInternalModelTextMessage(ex.toString()));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("OK");
    }


}





