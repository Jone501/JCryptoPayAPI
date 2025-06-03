package me.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import me.jone501.cryptopay.responses.BaseResponse;
import me.jone501.cryptopay.types.Check;
import me.jone501.cryptopay.types.Check.CheckStatus;
import me.jone501.cryptopay.types.Currency;
import me.jone501.cryptopay.types.Invoice;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GetCheckRequest extends Request<BaseResponse<Check>> {
    @SerializedName("check_ids")
    private String checkId;

    public GetCheckRequest(long checkId) {
        this.checkId = String.valueOf(checkId);
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
    protected BaseResponse<Check> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Check.class) {
            @Override
            protected Check parseJson(JsonElement json) {
                Check[] checks = GSON.fromJson(json.getAsJsonObject().get("items"), Check[].class);
                if (checks.length > 0)
                    return checks[0];
                return null;
            }
        };
    }
}
