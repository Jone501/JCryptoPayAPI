package ru.jone501.cryptopay.types;

import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppInfo {
    @SerializedName("app_id")
    long appId;

    @SerializedName("name")
    String name;

    @SerializedName("payment_processing_bot_username")
    String paymentProcessingBotUsername;
}
