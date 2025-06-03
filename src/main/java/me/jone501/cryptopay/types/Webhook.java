package me.jone501.cryptopay.types;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.apache.commons.codec.digest.HmacUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Getter
@ToString
@Accessors(fluent = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Webhook {
    @SerializedName("update_id")
    long updateId;

    @SerializedName("update_type")
    UpdateType updateType;

    @SerializedName("request_date")
    Date requestDate;

    @SerializedName("payload")
    Invoice payload;

    public enum UpdateType {
        @SerializedName("invoice_paid")
        INVOICE_PAID
    }

    public static Webhook fromJson(String json) {
        return new Gson().fromJson(json, Webhook.class);
    }

    public static Webhook fromJson(byte[] json) {
        return Webhook.fromJson(new String(json, StandardCharsets.UTF_8));
    }

    public static boolean checkSignature(String signature, String token, String content) {
        return checkSignature(signature, token, content.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean checkSignature(String signature, String token, byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] secret = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            String hmac = new HmacUtils("HmacSHA256", secret).hmacHex(content);
            return hmac.equals(signature);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
