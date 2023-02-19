package com.kyriakos.exceptions;


public class ExchangeRateApplicationException extends Exception {
    public ExchangeRateApplicationException() {
    }

    public String getErrorForInvalidCurrencyCodeTextMessage(String currencyCode) {return "{\"error\":\"The provided currency code:"+currencyCode+" is wrong.\"}";}

    public String getErrorForInvalidAmountTextMessage(Float amount) {return "{\"error\":\"The provided amount:"+amount+" is wrong, the amount should be a positive number.\"}";}

    public String getErrorForAmountTooLargeTextMessage(Float amount) {return "{\"error\":\"The provided amount:"+amount+" is too large.\"}";}

    public String missingParameterExceptionTextMessage(String parameter) {return "{\"error\":\"parameter: "+parameter+" is mandatory for this request.\"}";}

    public String wrongInternalModelTextMessage(String message) {
        return "{\"error\":\""+message+"\"}";
    }

}

