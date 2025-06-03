package me.jone501.cryptopay.types;

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
public class Error {
    @SerializedName("code")
    int code;

    @SerializedName("name")
    String name;

    @SerializedName("description")
    String description;

    @SerializedName("message")
    String message;
}
