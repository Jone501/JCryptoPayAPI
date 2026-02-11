package ru.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import ru.jone501.cryptopay.responses.BaseResponse;

import java.nio.charset.StandardCharsets;

public class DeleteInvoiceRequest extends Request<BaseResponse<Boolean>> {
    @SerializedName("invoice_id")
    private final long invoiceId;

    public DeleteInvoiceRequest(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String method() {
        return "deleteInvoice";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<Boolean> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Boolean.class);
    }
}
