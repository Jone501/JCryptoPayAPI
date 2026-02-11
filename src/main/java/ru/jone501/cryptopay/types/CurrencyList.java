package ru.jone501.cryptopay.types;

import ru.jone501.cryptopay.types.Currency.CurrencyType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrencyList implements Iterable<Currency> {
    private final Set<Currency> currencies;

    private CurrencyList(Set<Currency> currencies) {
        this.currencies = currencies;
    }

    public CurrencyList(Currency... currencies) {
        this(Arrays.stream(currencies)
                .collect(Collectors.toSet()));
    }

    public Currency single() {
        for (Currency currency : currencies)
            return currency;
        return null;
    }

    public CurrencyList byType(CurrencyType... types) {
        return new CurrencyList(currencies.stream()
                .filter(c -> Arrays.stream(types)
                        .anyMatch(x -> x == c.type()))
                .toArray(Currency[]::new));
    }

    public Currency byCode(String code) {
        if (code == null) return null;
        for (Currency currency : currencies)
            if (currency.code().equalsIgnoreCase(code))
                return currency;
        return null;
    }

    public CurrencyList byCode(String... codes) {
        return new CurrencyList(currencies.stream()
                .filter(c -> Arrays.stream(codes)
                        .anyMatch(x -> x.equalsIgnoreCase(c.code())))
                .toArray(Currency[]::new));
    }

    public CurrencyList withUrl() {
        return new CurrencyList(currencies.stream()
                .filter(c -> c.url() != null)
                .toArray(Currency[]::new));
    }

    public CurrencyList withoutUrl() {
        return new CurrencyList(currencies.stream()
                .filter(c -> c.url() == null)
                .toArray(Currency[]::new));
    }

    public CurrencyList union(CurrencyList other) {
        Set<Currency> newCurrencies = new HashSet<>(currencies);
        newCurrencies.addAll(other.currencies);
        return new CurrencyList(newCurrencies);
    }

    @Override
    public Iterator<Currency> iterator() {
        return currencies.iterator();
    }

    public Stream<Currency> stream() {
        return currencies.stream();
    }
}
