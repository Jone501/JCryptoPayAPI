package me.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import me.jone501.cryptopay.responses.BaseResponse;
import me.jone501.cryptopay.types.Currency;
import me.jone501.cryptopay.types.Invoice;
import me.jone501.cryptopay.types.Invoice.InvoiceStatus;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GetInvoiceRequest extends Request<BaseResponse<Invoice>> {
    @SerializedName("invoice_ids")
    private String invoiceId;

    public GetInvoiceRequest(long invoiceId) {
        this.invoiceId = String.valueOf(invoiceId);
    }

    @Override
    public String method() {
        return "getInvoices";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<Invoice> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Invoice.class) {
            @Override
            protected Invoice parseJson(JsonElement json) {
                Invoice[] invoices = GSON.fromJson(json.getAsJsonObject().get("items"), Invoice[].class);
                if (invoices.length > 0)
                    return invoices[0];
                return null;
            }
        };
    }
}
