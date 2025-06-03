package me.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import me.jone501.cryptopay.responses.BaseResponse;

import java.nio.charset.StandardCharsets;

public class DeleteCheckRequest extends Request<BaseResponse<Boolean>> {
    @SerializedName("check_id")
    private final long checkId;

    public DeleteCheckRequest(long checkId) {
        this.checkId = checkId;
    }

    @Override
    public String method() {
        return "deleteCheck";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<Boolean> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Boolean.class);
    }
}
