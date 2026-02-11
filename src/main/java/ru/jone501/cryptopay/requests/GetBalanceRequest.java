package ru.jone501.cryptopay.requests;

import com.google.gson.JsonElement;
import ru.jone501.cryptopay.responses.BaseResponse;
import ru.jone501.cryptopay.types.Balance;

public class GetBalanceRequest extends Request<BaseResponse<Balance[]>> {
    @Override
    public String method() {
        return "getBalance";
    }

    @Override
    protected BaseResponse<Balance[]> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Balance[].class);
    }
}
