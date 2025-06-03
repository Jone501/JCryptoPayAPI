package me.jone501.cryptopay.types;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import me.jone501.cryptopay.Currencies;

import java.net.URL;
import java.util.Date;

@ToString
@Accessors(fluent = true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Check {
    @Getter
    @SerializedName("check_id")
    long checkId;

    @Getter
    @SerializedName("hash")
    String hash;

    @ToString.Exclude
    @SerializedName("asset")
    String asset;

    @ToString.Include
    public Currency asset() {
        return Currencies.byCode(asset);
    }

    @Getter
    @SerializedName("amount")
    double amount;

    @Getter
    @SerializedName("bot_check_url")
    URL botCheckUrl;

    @Getter
    @SerializedName("status")
    CheckStatus status;

    @Getter
    @SerializedName("created_at")
    Date createdAt;

    @Getter
    @SerializedName("activated_at")
    Date activatedAt;

    public enum CheckStatus {
        @SerializedName("active")
        ACTIVE,
        @SerializedName("activated")
        ACTIVATED
    }
}