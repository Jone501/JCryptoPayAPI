package ru.jone501.cryptopay.polling;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.jone501.cryptopay.CryptoApp;
import ru.jone501.cryptopay.types.Error;

import java.util.TimerTask;
import java.util.function.Consumer;

@Setter
@Getter(AccessLevel.PACKAGE)
@Accessors(fluent = true)
public abstract class PollingTracker<T> {
    @Getter(AccessLevel.PROTECTED)
    private final CryptoApp client;
    @Setter(AccessLevel.PACKAGE)
    private TimerTask task;
    private Runnable trackEnds = () -> {};
    private Consumer<Error> onError = x -> {};

    public PollingTracker(CryptoApp client) {
        this.client = client;
    }

    protected abstract void test(T object);

    protected final void stop() {
        if (task != null)
            task.cancel();
    }

    protected final void error(Error error) {
        onError.accept(error);
        stop();
    }
}
