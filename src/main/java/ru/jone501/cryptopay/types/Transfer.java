package ru.jone501.cryptopay.types;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import ru.jone501.cryptopay.Currencies;

import java.util.Date;

@ToString
@Accessors(fluent = true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Transfer {
    @Getter
    @SerializedName("transfer_id")
    long transferId;

    @Getter
    @SerializedName("spend_id")
    String spendId;

    @Getter
    @SerializedName("user_id")
    String userId;

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
    @SerializedName("status")
    TransferStatus status;

    @Getter
    @SerializedName("completed_at")
    Date completedAt;

    @Getter
    @SerializedName("comment")
    String comment;

    public enum TransferStatus {
        @SerializedName("completed")
        COMPLETED
    }
}