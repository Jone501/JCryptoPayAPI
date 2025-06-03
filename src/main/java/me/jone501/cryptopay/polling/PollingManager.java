package me.jone501.cryptopay.polling;

import me.jone501.cryptopay.CryptoApp;
import me.jone501.cryptopay.types.Check;
import me.jone501.cryptopay.types.Invoice;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class PollingManager {
    private final CryptoApp client;
    private final PollingConfiguration configuration;

    public PollingManager(CryptoApp client, PollingConfiguration configuration) {
        this.client = client;
        this.configuration = configuration;
    }

    public <T, P extends PollingTracker<T>> P track(T object, P tracker, Duration endsAfter) {
        AtomicLong time = new AtomicLong();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                time.addAndGet(configuration.period());
                if ((endsAfter != null && time.get() >= endsAfter.toMillis())
                        || (configuration.maxTrackerLifetime() > 0 && time.get() >= configuration.maxTrackerLifetime())) {
                    tracker.trackEnds().run();
                    tracker.stop();
                    return;
                }
                tracker.test(object);
            }
        };
        tracker.task(task);
        new Timer().schedule(task, configuration.period(), configuration.period());
        return tracker;
    }

    public <T, P extends PollingTracker<T>> P track(T object, P tracker) {
        return track(object, tracker, null);
    }

    public InvoicePollingTracker trackInvoice(Invoice invoice, Duration endsAfter) {
        return track(invoice, new InvoicePollingTracker(client), endsAfter);
    }

    public InvoicePollingTracker trackInvoice(Invoice invoice) {
        return track(invoice, new InvoicePollingTracker(client));
    }

    public CheckPollingTracker trackCheck(Check check, Duration endsAfter) {
        return track(check, new CheckPollingTracker(client), endsAfter);
    }

    public CheckPollingTracker trackCheck(Check check) {
        return track(check, new CheckPollingTracker(client));
    }
}