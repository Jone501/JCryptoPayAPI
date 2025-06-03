package me.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import me.jone501.cryptopay.types.Invoice.InvoiceStatus;
import me.jone501.cryptopay.responses.BaseResponse;
import me.jone501.cryptopay.types.Currency;
import me.jone501.cryptopay.types.Invoice;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class GetInvoicesRequest extends Request<BaseResponse<Invoice[]>> {
    @SerializedName("asset")
    private String asset;

    @SerializedName("fiat")
    private String fiat;

    @SerializedName("invoice_ids")
    private String invoiceIds;

    @SerializedName("status")
    private InvoiceStatus status;

    @SerializedName("offset")
    private String offset;

    @SerializedName("count")
    private String count;

    private GetInvoicesRequest() {
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
    protected BaseResponse<Invoice[]> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Invoice[].class) {
            @Override
            protected Invoice[] parseJson(JsonElement json) {
                return GSON.fromJson(json.getAsJsonObject().get("items"), type());
            }
        };
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        GetInvoicesRequest request = new GetInvoicesRequest();

        private Builder() {
        }

        public Builder currency(Currency currency) {
            if (currency.isCrypto()) {
                request.asset = currency.code();
                request.fiat = null;
            } else {
                request.asset = null;
                request.fiat = currency.code();
            }
            return this;
        }

        public Builder invoiceIds(long... invoiceIds) {
            request.invoiceIds = String.join(",", Arrays.stream(invoiceIds)
                    .mapToObj(String::valueOf)
                    .toList());
            return this;
        }

        public Builder status(InvoiceStatus status) {
            if (status == InvoiceStatus.EXPIRED)
                throw new IllegalArgumentException();
            request.status = status;
            return this;
        }

        public Builder offset(long offset) {
            if (offset < 0)
                throw new IllegalArgumentException();
            request.offset = String.valueOf(offset);
            return this;
        }

        public Builder count(int count) {
            if (count < 1 || count > 1000)
                throw new IllegalArgumentException();
            request.count = String.valueOf(count);
            return this;
        }

        public GetInvoicesRequest build() {
            return request;
        }
    }
}
