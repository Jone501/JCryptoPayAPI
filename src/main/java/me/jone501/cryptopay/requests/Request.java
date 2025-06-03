package me.jone501.cryptopay.requests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.nio.charset.StandardCharsets;

@Getter
@Accessors(fluent = true)
public abstract class Request<R> {
    public final R parseResponse(byte[] response) {
        return parseJsonResponse(JsonParser.parseString(new String(response, StandardCharsets.UTF_8)));
    }

    public byte[] content() {
        return new byte[0];
    }

    public abstract String method();

    protected abstract R parseJsonResponse(JsonElement json);
}
