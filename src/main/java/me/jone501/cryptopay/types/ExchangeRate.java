package me.jone501.cryptopay.types;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import me.jone501.cryptopay.Currencies;

@ToString
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExchangeRate {
    @Getter
    @SerializedName("is_valid")
    boolean isValid;

    @Getter
    @SerializedName("is_crypto")
    boolean isCrypto;

    @Getter
    @SerializedName("is_fiat")
    boolean isFiat;

    @SerializedName("source")
    String source;

    @ToString.Include
    public Currency source() {
        return Currencies.byCode(source);
    }

    @SerializedName("target")
    String target;

    @ToString.Include
    public Currency target() {
        return Currencies.byCode(target);
    }

    @Getter
    @SerializedName("rate")
    double rate;

    public ExchangeRate reverse() {
        ExchangeRate reversed = new ExchangeRate();
        reversed.source = target;
        reversed.target = source;
        reversed.isCrypto = reversed.source().isCrypto();
        reversed.isFiat = reversed.source().isFiat();
        reversed.isValid = (reversed.isCrypto && reversed.target().isFiat())
                || (reversed.isFiat && reversed.target().equals(Currencies.USD()));
        reversed.rate = 1 / rate;
        return reversed;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExchangeRate other)) return false;
        return source.equalsIgnoreCase(other.source) &&
                target.equalsIgnoreCase(other.target);
    }
}
