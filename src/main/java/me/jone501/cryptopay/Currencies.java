package me.jone501.cryptopay;

import me.jone501.cryptopay.types.Currency.CurrencyType;
import me.jone501.cryptopay.types.Currency;
import me.jone501.cryptopay.types.CurrencyList;

public class Currencies {
    private static CurrencyList currencies = new CurrencyList();

    static void init(CurrencyList currencies) {
        Currencies.currencies = currencies;
    }

    public static CurrencyList all() {
        return currencies;
    }

    public static CurrencyList stablecoins() {
        return currencies.byType(CurrencyType.STABLECOIN);
    }

    public static CurrencyList blockchain() {
        return currencies.byType(CurrencyType.BLOCKCHAIN);
    }

    public static CurrencyList crypto() {
        return currencies.byType(CurrencyType.BLOCKCHAIN, CurrencyType.STABLECOIN);
    }

    public static CurrencyList fiat() {
        return currencies.byType(CurrencyType.FIAT);
    }

    public static Currency byCode(String code) {
        if (currencies == null) return null;
        return currencies.byCode(code);
    }

    public static Currency BTC() {
        return byCode("BTC");
    }

    public static Currency LTC() {
        return byCode("LTC");
    }

    public static Currency BNB() {
        return byCode("BNB");
    }

    public static Currency DOGE() {
        return byCode("DOGE");
    }

    public static Currency ETH() {
        return byCode("ETH");
    }

    public static Currency TRX() {
        return byCode("TRX");
    }

    public static Currency TON() {
        return byCode("TON");
    }

    public static Currency SEND() {
        return byCode("SEND");
    }

    public static Currency USDT() {
        return byCode("USDT");
    }

    public static Currency JET() {
        return byCode("JET");
    }

    public static Currency USDC() {
        return byCode("USDC");
    }

    public static Currency IDR() {
        return byCode("IDR");
    }

    public static Currency PLN() {
        return byCode("PLN");
    }

    public static Currency UAH() {
        return byCode("UAH");
    }

    public static Currency AMD() {
        return byCode("AMD");
    }

    public static Currency BRL() {
        return byCode("BRL");
    }

    public static Currency AED() {
        return byCode("AED");
    }

    public static Currency TJS() {
        return byCode("TJS");
    }

    public static Currency UZS() {
        return byCode("UZS");
    }

    public static Currency RUB() {
        return byCode("RUB");
    }

    public static Currency USD() {
        return byCode("USD");
    }

    public static Currency GBP() {
        return byCode("GBP");
    }

    public static Currency TRY() {
        return byCode("TRY");
    }

    public static Currency BYN() {
        return byCode("BYN");
    }

    public static Currency ILS() {
        return byCode("ILS");
    }

    public static Currency CNY() {
        return byCode("CNY");
    }

    public static Currency GEL() {
        return byCode("GEL");
    }

    public static Currency EUR() {
        return byCode("EUR");
    }

    public static Currency KZT() {
        return byCode("KZT");
    }

    public static Currency AZN() {
        return byCode("AZN");
    }

    public static Currency INR() {
        return byCode("INR");
    }

    public static Currency THB() {
        return byCode("THB");
    }

    public static Currency KGS() {
        return byCode("KGS");
    }
}