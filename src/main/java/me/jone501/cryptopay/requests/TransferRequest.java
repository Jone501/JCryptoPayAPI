package me.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import me.jone501.cryptopay.responses.BaseResponse;
import me.jone501.cryptopay.types.Currency;
import me.jone501.cryptopay.types.Transfer;

import java.nio.charset.StandardCharsets;

public class TransferRequest extends Request<BaseResponse<Transfer>> {
    @SerializedName("user_id")
    private long userId;

    @SerializedName("asset")
    private String asset;

    @SerializedName("amount")
    private double amount;

    @SerializedName("spend_id")
    private String spendId;

    @SerializedName("comment")
    private String comment;

    @SerializedName("disable_send_notification")
    private String disableSendNotification;

    private TransferRequest() {
    }

    @Override
    public String method() {
        return "transfer";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<Transfer> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Transfer.class);
    }

    public static Builder builder(long userId, Currency asset, double amount, String spendId) {
        return new Builder(userId, asset, amount, spendId);
    }

    public static class Builder {
        TransferRequest request = new TransferRequest();

        private Builder(long userId, Currency asset, double amount, String spendId) {
            userId(userId).asset(asset).amount(amount).spendId(spendId);
        }

        public Builder userId(long userId) {
            request.userId = userId;
            return this;
        }

        public Builder asset(Currency asset) {
            if (asset.isFiat())
                throw new IllegalArgumentException();
            request.asset = asset.code();
            return this;
        }

        public Builder amount(double amount) {
            if (amount < 0)
                throw new IllegalArgumentException();
            request.amount = amount;
            return this;
        }

        public Builder spendId(String spendId) {
            if (spendId.length() > 64)
                throw new IllegalArgumentException();
            request.spendId = spendId;
            return this;
        }

        public Builder comment(String comment) {
            if (comment.length() > 1024)
                throw new IllegalArgumentException();
            request.comment = comment;
            return this;
        }

        public Builder disableSendNotification(boolean disableSendNotification) {
            request.disableSendNotification = String.valueOf(disableSendNotification);
            return this;
        }

        public TransferRequest build() {
            return request;
        }
    }
}
