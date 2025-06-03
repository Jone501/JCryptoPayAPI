package me.jone501.cryptopay.requests;

import com.google.gson.JsonElement;
import me.jone501.cryptopay.responses.BaseResponse;
import me.jone501.cryptopay.types.ExchangeRate;
import me.jone501.cryptopay.types.ExchangeRateList;

public class GetExchangeRatesRequest extends Request<BaseResponse<ExchangeRateList>> {
    @Override
    public String method() {
        return "getExchangeRates";
    }

    @Override
    protected BaseResponse<ExchangeRateList> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, ExchangeRateList.class) {
            @Override
            protected ExchangeRateList parseJson(JsonElement json) {
                return new ExchangeRateList(GSON.fromJson(json, ExchangeRate[].class));
            }
        };
    }
}
