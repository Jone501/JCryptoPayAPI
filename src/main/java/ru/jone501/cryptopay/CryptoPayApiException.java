package ru.jone501.cryptopay;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import ru.jone501.cryptopay.types.Error;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public class CryptoPayApiException extends Exception {
    private final Error error;

    @Override
    public String getMessage() {
        return error.toString();
    }
}
