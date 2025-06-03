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
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Balance {
    @ToString.Exclude
    @SerializedName("currency_code")
    String currencyCode;

    @ToString.Include
    public Currency currency() {
        return Currencies.byCode(currencyCode);
    }

    @Getter
    @SerializedName("available")
    double available;

    @Getter
    @SerializedName("onhold")
    double onhold;
}