package me.jone501.cryptopay.requests;

import com.google.gson.JsonElement;
import me.jone501.cryptopay.responses.BaseResponse;
import me.jone501.cryptopay.types.Currency;
import me.jone501.cryptopay.types.CurrencyList;

public class GetCurrenciesRequest extends Request<BaseResponse<CurrencyList>> {
    @Override
    public String method() {
        return "getCurrencies";
    }

    @Override
    protected BaseResponse<CurrencyList> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, CurrencyList.class) {
            @Override
            protected CurrencyList parseJson(JsonElement json) {
                return new CurrencyList(GSON.fromJson(json, Currency[].class));
            }
        };
    }
}
