package ru.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import ru.jone501.cryptopay.responses.BaseResponse;
import ru.jone501.cryptopay.types.Check;
import ru.jone501.cryptopay.types.Currency;

import java.nio.charset.StandardCharsets;

public class CreateCheckRequest extends Request<BaseResponse<Check>> {
    @SerializedName("asset")
    private String asset;

    @SerializedName("amount")
    private double amount;

    @SerializedName("pin_to_user_id")
    private long pinToUserId;

    @SerializedName("pin_to_username")
    private String pinToUsername;

    private CreateCheckRequest() {
    }

    @Override
    public String method() {
        return "createCheck";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<Check> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Check.class);
    }

    public static Builder builder(Currency asset, double amount) {
        return new Builder(asset, amount);
    }

    public static class Builder {
        CreateCheckRequest request = new CreateCheckRequest();

        private Builder(Currency asset, double amount) {
            asset(asset).amount(amount);
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

        public Builder pinToUserId(long pinToUserId) {
            request.pinToUserId = pinToUserId;
            return this;
        }

        public Builder pinToUsername(String pinToUsername) {
            request.pinToUsername = pinToUsername;
            return this;
        }

        public CreateCheckRequest build() {
            return request;
        }
    }
}
