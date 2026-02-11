package ru.jone501.cryptopay.types;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.net.URL;

@ToString
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Currency {
    @ToString.Exclude
    @SerializedName("is_blockchain")
    boolean isBlockchain;

    @ToString.Exclude
    @SerializedName("is_stablecoin")
    boolean isStablecoin;

    @ToString.Exclude
    @SerializedName("is_fiat")
    boolean isFiat;

    @ToString.Include
    public CurrencyType type() {
        if (isBlockchain) return CurrencyType.BLOCKCHAIN;
        if (isStablecoin) return CurrencyType.STABLECOIN;
        return CurrencyType.FIAT;
    }

    @Getter
    @SerializedName("name")
    String name;

    @Getter
    @SerializedName("code")
    String code;

    @Getter
    @SerializedName("url")
    URL url;

    @Getter
    @SerializedName("decimals")
    int decimals;

    public boolean isCrypto() {
        return isBlockchain || isStablecoin;
    }

    public boolean isFiat() {
        return isFiat;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Currency other)) return false;
        return code.equalsIgnoreCase(other.code);
    }

    public enum CurrencyType {
        BLOCKCHAIN,
        STABLECOIN,
        FIAT
    }
}
