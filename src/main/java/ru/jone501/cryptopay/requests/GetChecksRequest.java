package ru.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import ru.jone501.cryptopay.types.Check.CheckStatus;
import ru.jone501.cryptopay.responses.BaseResponse;
import ru.jone501.cryptopay.types.Check;
import ru.jone501.cryptopay.types.Currency;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GetChecksRequest extends Request<BaseResponse<Check[]>> {
    @SerializedName("asset")
    private String asset;

    @SerializedName("check_ids")
    private String checkIds;

    @SerializedName("status")
    private CheckStatus status;

    @SerializedName("offset")
    private String offset;

    @SerializedName("count")
    private String count;

    private GetChecksRequest() {
    }

    @Override
    public String method() {
        return "getChecks";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<Check[]> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Check[].class) {
            @Override
            protected Check[] parseJson(JsonElement json) {
                return GSON.fromJson(json.getAsJsonObject().get("items"), type());
            }
        };
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        GetChecksRequest request = new GetChecksRequest();

        private Builder() {
        }

        public Builder asset(Currency asset) {
            if (asset.isFiat())
                throw new IllegalArgumentException();
            request.asset = asset.code();
            return this;
        }

        public Builder checkIds(long... checkIds) {
            request.checkIds = String.join(",", Arrays.stream(checkIds)
                    .mapToObj(String::valueOf)
                    .toList());
            return this;
        }

        public Builder status(CheckStatus status) {
            request.status = status;
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

        public GetChecksRequest build() {
            return request;
        }
    }
}
