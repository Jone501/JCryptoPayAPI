package ru.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import ru.jone501.cryptopay.responses.BaseResponse;
import ru.jone501.cryptopay.types.Currency;
import ru.jone501.cryptopay.types.Transfer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GetTransfersRequest extends Request<BaseResponse<Transfer[]>> {
    @SerializedName("asset")
    private String asset;

    @SerializedName("transfer_ids")
    private String transferIds;

    @SerializedName("spend_id")
    private String spendId;

    @SerializedName("offset")
    private String offset;

    @SerializedName("count")
    private String count;

    private GetTransfersRequest() {
    }

    @Override
    public String method() {
        return "getTransfers";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<Transfer[]> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Transfer[].class) {
            @Override
            protected Transfer[] parseJson(JsonElement json) {
                return GSON.fromJson(json.getAsJsonObject().get("items"), type());
            }
        };
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        GetTransfersRequest request = new GetTransfersRequest();

        private Builder() {
        }

        public Builder asset(Currency asset) {
            if (asset.isFiat())
                throw new IllegalArgumentException();
            request.asset = asset.code();
            return this;
        }

        public Builder transferIds(long... transferIds) {
            request.transferIds = String.join(",", Arrays.stream(transferIds)
                    .mapToObj(String::valueOf)
                    .toList());
            return this;
        }

        public Builder spendId(String spendId) {
            request.spendId = spendId;
            return this;
        }

        public Builder offset(long offset) {
            if (offset < 0)
                throw new IllegalArgumentException();
            request.offset = String.valueOf(offset);
            return this;
        }

        public Builder count(int count) {
            if (count < 1 || count > 1000)
                throw new IllegalArgumentException();
            request.count = String.valueOf(count);
            return this;
        }

        public GetTransfersRequest build() {
            return request;
        }
    }
}
