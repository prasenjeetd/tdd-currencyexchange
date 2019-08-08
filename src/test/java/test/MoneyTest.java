package test;

import net.pdutta.multimoney.CurrencyExchange;
import net.pdutta.multimoney.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    private CurrencyExchange cx;

    MoneyTest () {
        cx = CurrencyExchange.builder()
                .withName("Swiss Holidays Currency Exchange")
                .withRate(Money.CurrencyName.USD, Money.CurrencyName.CHF, "0.97")
                .withRate(Money.CurrencyName.CHF, Money.CurrencyName.USD, "1.01")
                .build();
    }


    @Test
    @DisplayName("Basic Currency Exchange")
    void basicCurrencyExchange () {
        Money usd = Money.build("12.65", Money.CurrencyName.USD);
        Money chf = Money.build("6.30", Money.CurrencyName.CHF);
        Money usdToChf = cx.convert(usd, Money.CurrencyName.CHF);
        Money chfToUsd = cx.convert(chf, Money.CurrencyName.USD);
        assertEquals(Money.build("12.27", Money.CurrencyName.CHF), usdToChf);
        assertEquals(Money.build("6.36", Money.CurrencyName.USD), chfToUsd);
    }

    @Test
    @DisplayName("Error Conditions")
    void errorConditions () {
        assertThrows(NumberFormatException.class, () -> Money.build("$12", Money.CurrencyName.CAD));
        Money usd = Money.build("12.65", Money.CurrencyName.USD);
        assertThrows(IllegalArgumentException.class, () -> cx.convert(usd, Money.CurrencyName.CAD));
    }

}