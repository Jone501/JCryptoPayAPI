package ru.jone501.cryptopay.responses;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.experimental.Accessors;
import ru.jone501.cryptopay.types.Error;

@Getter
@Accessors(fluent = true)
public class BaseResponse<R> extends Response {
    protected static final Gson GSON = new Gson();

    private final boolean ok;
    private final Class<R> type;
    private R result;
    private Error error;

    public BaseResponse(JsonElement json, Class<R> type) {
        super(json);
        this.type = type;
        JsonObject object = json.getAsJsonObject();
        ok = object.get("ok").getAsBoolean();
        if (ok) result = parseJson(object.get("result"));
        else error = GSON.fromJson(object.get("error"), Error.class);
    }

    protected R parseJson(JsonElement json) {
        return GSON.fromJson(json, type);
    }
}
