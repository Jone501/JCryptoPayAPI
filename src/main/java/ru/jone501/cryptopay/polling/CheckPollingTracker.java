package ru.jone501.cryptopay.polling;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.jone501.cryptopay.CryptoApp;
import ru.jone501.cryptopay.responses.ResponseHandler;
import ru.jone501.cryptopay.types.Check;
import ru.jone501.cryptopay.types.Error;

import java.util.function.Consumer;

@Setter
@Getter(AccessLevel.PACKAGE)
@Accessors(fluent = true)
public class CheckPollingTracker extends PollingTracker<Check> {
    private Consumer<Check> checkActivated = x -> {};
    private Runnable checkDeleted = () -> {};

    @Override
    public CheckPollingTracker trackEnds(Runnable trackEnds) {
        super.trackEnds(trackEnds);
        return this;
    }

    @Override
    public CheckPollingTracker onError(Consumer<Error> onError) {
        super.onError(onError);
        return this;
    }

    public CheckPollingTracker(CryptoApp client) {
        super(client);
    }

    @Override
    protected void test(Check object) {
        client().getCheckAsync(object.checkId(), ResponseHandler.create(check -> {
            if (check == null) {
                checkDeleted.run();
                stop();
            } else if (check.status() == Check.CheckStatus.ACTIVATED) {
                checkActivated.accept(check);
                stop();
            }
        }, this::error));
    }
}
