package ru.jone501.cryptopay.types;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@ToString
@Accessors(fluent = true)
@FieldDefaults(level=AccessLevel.PRIVATE)
public class AppStats {
    @SerializedName("volume")
    double volume;

    @SerializedName("conversion")
    double conversion;

    @SerializedName("unique_users_count")
    long uniqueUsersCount;

    @SerializedName("created_invoice_count")
    long createdInvoiceCount;

    @SerializedName("paid_invoice_count")
    long paidInvoiceCount;

    @SerializedName("start_at")
    Date startAt;

    @SerializedName("end_at")
    Date endAt;
}
