package com.kyriakos.models.internal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@RedisHash("Currency")
public class Currency {

    @Id String id;

    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("base")
    @Expose
    private String base;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("amount")
    @Expose
    private Float amount;
    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("result")
    @Expose
    private Double result;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("symbols")
    @Expose
    private List<String> symbols;
    @SerializedName("rates")
    @Expose
    private Rates rates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Currency.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("from");
        sb.append('=');
        sb.append(((this.from == null) ? "<null>" : this.from));
        sb.append(',');
        sb.append("base");
        sb.append('=');
        sb.append(((this.base == null) ? "<null>" : this.base));
        sb.append(',');
        sb.append("to");
        sb.append('=');
        sb.append(((this.to == null) ? "<null>" : this.to));
        sb.append(',');
        sb.append("amount");
        sb.append('=');
        sb.append(((this.amount == null) ? "<null>" : this.amount));
        sb.append(',');
        sb.append("rate");
        sb.append('=');
        sb.append(((this.rate == null) ? "<null>" : this.rate));
        sb.append(',');
        sb.append("result");
        sb.append('=');
        sb.append(((this.result == null) ? "<null>" : this.result));
        sb.append(',');
        sb.append("date");
        sb.append('=');
        sb.append(((this.date == null) ? "<null>" : this.date));
        sb.append(',');
        sb.append("symbols");
        sb.append('=');
        sb.append(((this.symbols == null) ? "<null>" : this.symbols));
        sb.append(',');
        sb.append("rates");
        sb.append('=');
        sb.append(((this.rates == null) ? "<null>" : this.rates));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
