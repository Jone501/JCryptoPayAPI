package me.jone501.cryptopay.polling;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.jone501.cryptopay.CryptoApp;
import me.jone501.cryptopay.responses.ResponseHandler;
import me.jone501.cryptopay.types.Error;
import me.jone501.cryptopay.types.Invoice;

import java.util.function.Consumer;

@Setter
@Getter(AccessLevel.PACKAGE)
@Accessors(fluent = true)
public class InvoicePollingTracker extends PollingTracker<Invoice> {
    private Consumer<Invoice>
            invoicePaid = x -> {},
            invoiceExpired = x -> {};
    private Runnable invoiceDeleted = () -> {};

    @Override
    public InvoicePollingTracker trackEnds(Runnable trackEnds) {
        super.trackEnds(trackEnds);
        return this;
    }

    @Override
    public InvoicePollingTracker onError(Consumer<Error> onError) {
        super.onError(onError);
        return this;
    }

    public InvoicePollingTracker(CryptoApp client) {
        super(client);
    }

    @Override
    protected void test(Invoice object) {
        client().getInvoiceAsync(object.invoiceId(), ResponseHandler.create(invoice -> {
            if (invoice == null) {
                invoiceDeleted.run();
                stop();
            } else if (invoice.status() == Invoice.InvoiceStatus.PAID) {
                invoicePaid.accept(invoice);
                stop();
            } else if (invoice.status() == Invoice.InvoiceStatus.EXPIRED) {
                invoiceExpired.accept(invoice);
                stop();
            }
        }, this::error));
    }
}
