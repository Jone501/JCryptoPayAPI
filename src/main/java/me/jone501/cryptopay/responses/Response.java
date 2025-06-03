package me.jone501.cryptopay.responses;

import com.google.gson.JsonElement;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class Response {
    private final JsonElement json;

    public Response(JsonElement json) {
        this.json = json;
    }

    @Override
    public String toString() {
        return json.toString();
    }
}
