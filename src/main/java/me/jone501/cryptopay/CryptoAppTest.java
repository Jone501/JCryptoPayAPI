package me.jone501.cryptopay;

import me.jone501.cryptopay.polling.PollingConfiguration;

public class CryptoAppTest extends CryptoApp {
    public CryptoAppTest(String token, PollingConfiguration configuration) {
        super("testnet-pay.crypt.bot/api/", token, configuration);
    }

    public CryptoAppTest(String token) {
        this(token, new PollingConfiguration());
    }
}
