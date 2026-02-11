package ru.jone501.cryptopay.requests;

import com.google.gson.JsonElement;
import ru.jone501.cryptopay.responses.BaseResponse;
import ru.jone501.cryptopay.types.AppInfo;

public class GetMeRequest extends Request<BaseResponse<AppInfo>> {
    @Override
    public String method() {
        return "getMe";
    }

    @Override
    protected BaseResponse<AppInfo> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, AppInfo.class);
    }
}
