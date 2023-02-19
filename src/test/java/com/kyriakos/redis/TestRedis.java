package com.kyriakos.redis;

import com.kyriakos.models.internal.Currency;
import com.kyriakos.repositories.CurrencyRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TestRedis {

    @MockBean
    CurrencyRepository currencyRepository;


    /* Test if record is saved in Redis */
    @Test
    public void testRedis() {

        Currency currency = new Currency();

        String MD5Hash = "a656a481f72e79066adb842131855306";
        String wrongMD5Hash = "11111111111111111111111111111111";

        currency.setFrom("EUR");
        currency.setTo("USD");
        currency.setId(MD5Hash);

        when(currencyRepository.findById(MD5Hash)).thenReturn(Optional.of(currency));

        Assert.assertTrue(currencyRepository.findById(MD5Hash).isPresent());
        Assert.assertFalse(currencyRepository.findById(wrongMD5Hash).isPresent());

    }

}
