package me.jone501.cryptopay.types;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExchangeRateList implements Iterable<ExchangeRate> {
    private final Set<ExchangeRate> rates;

    private ExchangeRateList(Set<ExchangeRate> rates) {
        this.rates = rates;
    }

    public ExchangeRateList(ExchangeRate... rates) {
        this(Arrays.stream(rates)
                .collect(Collectors.toSet()));
    }

    public ExchangeRate single() {
        for (ExchangeRate rate : rates)
            return rate;
        return null;
    }

    public ExchangeRate byPair(Currency source, Currency target) {
        for (ExchangeRate rate : rates)
            if (rate.source().equals(source) && rate.target().equals(target))
                return rate;
        return null;
    }

    public ExchangeRateList withSource(Currency... currencies) {
        return new ExchangeRateList(rates.stream()
                .filter(c -> Arrays.stream(currencies)
                        .anyMatch(x -> x.equals(c.source())))
                .toArray(ExchangeRate[]::new));
    }

    public ExchangeRateList withTarget(Currency... currencies) {
        return new ExchangeRateList(rates.stream()
                .filter(c -> Arrays.stream(currencies)
                        .anyMatch(x -> x.equals(c.target())))
                .toArray(ExchangeRate[]::new));
    }

    public ExchangeRateList containing(Currency... currencies) {
        return new ExchangeRateList(rates.stream()
                .filter(c -> Arrays.stream(currencies)
                        .anyMatch(x -> x.equals(c.target()) || x.equals(c.source())))
                .toArray(ExchangeRate[]::new));
    }

    public ExchangeRateList crypto() {
        return new ExchangeRateList(rates.stream()
                .filter(ExchangeRate::isCrypto)
                .toArray(ExchangeRate[]::new));
    }

    public ExchangeRateList fiat() {
        return new ExchangeRateList(rates.stream()
                .filter(ExchangeRate::isFiat)
                .toArray(ExchangeRate[]::new));
    }

    public ExchangeRateList union(ExchangeRateList other) {
        Set<ExchangeRate> newRates = new HashSet<>(rates);
        newRates.addAll(other.rates);
        return new ExchangeRateList(newRates);
    }

    @Override
    public Iterator<ExchangeRate> iterator() {
        return rates.iterator();
    }

    public Stream<ExchangeRate> stream() {
        return rates.stream();
    }
}
