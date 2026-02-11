package ru.jone501.cryptopay.types;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.jone501.cryptopay.Currencies;

import java.net.URL;
import java.util.Arrays;
import java.util.Date;

@ToString
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Invoice {
    @Getter
    @SerializedName("invoice_id")
    private long invoiceId;

    @Getter
    @SerializedName("hash")
    private String hash;

    @ToString.Exclude
    @SerializedName("currency_type")
    private String currencyType;

    @ToString.Exclude
    @SerializedName("asset")
    private String asset;

    @ToString.Exclude
    @SerializedName("fiat")
    private String fiat;

    @ToString.Include
    public Currency currency() {
        if (currencyType.equals("crypto"))
            return Currencies.byCode(asset);
        return Currencies.byCode(fiat);
    }

    @Getter
    @SerializedName("amount")
    private double amount;

    @ToString.Exclude
    @SerializedName("paid_asset")
    private String paidAsset;

    @ToString.Include
    public Currency paidAsset() {
        return Currencies.byCode(paidAsset);
    }

    @Getter
    @SerializedName("paid_amount")
    private double paidAmount;

    @Getter
    @SerializedName("paid_fiat_rate")
    private double paidFiatRate;

    @ToString.Exclude
    @SerializedName("accepted_assets")
    private String[] acceptedAssets;

    @ToString.Include
    public Currency[] acceptedAssets() {
        if (acceptedAssets == null)
            return new Currency[0];
        return Arrays.stream(acceptedAssets)
                .map(Currencies::byCode)
                .toArray(Currency[]::new);
    }

    @ToString.Exclude
    @SerializedName("fee_asset")
    private String feeAsset;

    @ToString.Include
    public Currency feeAsset() {
        return Currencies.byCode(feeAsset);
    }

    @Getter
    @SerializedName("fee_amount")
    private double feeAmount;

    @Getter
    @SerializedName("fee_in_usd")
    private double feeInUsd;

    @Getter
    @SerializedName("bot_invoice_url")
    private URL botInvoiceUrl;

    @Getter
    @SerializedName("mini_app_invoice_url")
    private URL miniAppInvoiceUrl;

    @Getter
    @SerializedName("web_app_invoice_url")
    private URL webAppInvoiceUrl;

    @Getter
    @SerializedName("description")
    private String description;

    @Getter
    @SerializedName("status")
    private InvoiceStatus status;

    @ToString.Exclude
    @SerializedName("swap_to")
    private String swapTo;

    @ToString.Include
    public Currency swapTo() {
        return Currencies.byCode(swapTo);
    }

    @Getter
    @SerializedName("is_swapped")
    private boolean isSwapped;

    @Getter
    @SerializedName("swapped_uid")
    private String swappedUid;

    @ToString.Exclude
    @SerializedName("swapped_to")
    private String swappedTo;

    @ToString.Include
    public Currency swappedTo() {
        return Currencies.byCode(swappedTo);
    }

    @Getter
    @SerializedName("swapped_rate")
    private double swappedRate;

    @Getter
    @SerializedName("swapped_output")
    private double swappedOutput;

    @Getter
    @SerializedName("swapped_usd_amount")
    private double swappedUsdAmount;

    @Getter
    @SerializedName("swapped_usd_rate")
    private double swappedUsdRate;

    @Getter
    @SerializedName("created_at")
    private Date createdAt;

    @Getter
    @SerializedName("paid_usd_rate")
    private double paidUsdRate;

    @Getter
    @SerializedName("allow_comments")
    private boolean allowComments;

    @Getter
    @SerializedName("allow_anonymous")
    private boolean allowAnonymous;

    @Getter
    @SerializedName("expiration_date")
    private Date expirationDate;

    @Getter
    @SerializedName("paid_at")
    private Date paidAt;

    @Getter
    @SerializedName("paid_anonymously")
    private boolean paidAnonymously;

    @Getter
    @SerializedName("comment")
    private String comment;

    @Getter
    @SerializedName("hidden_message")
    private String hiddenMessage;

    @Getter
    @SerializedName("payload")
    private String payload;

    @Getter
    @SerializedName("paid_btn_name")
    private PaidButtonName paidButtonName;

    @Getter
    @SerializedName("paid_btn_url")
    private URL paidButtonUrl;

    public enum InvoiceStatus {
        @SerializedName("active")
        ACTIVE,
        @SerializedName("paid")
        PAID,
        @SerializedName("expired")
        EXPIRED
    }

    public enum PaidButtonName {
        @SerializedName("viewItem")
        VIEW_ITEM,
        @SerializedName("openChannel")
        OPEN_CHANNEL,
        @SerializedName("openBot")
        OPEN_BOT,
        @SerializedName("callback")
        CALLBACK
    }
}