package me.jone501.cryptopay.requests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;
import me.jone501.cryptopay.types.Invoice.PaidButtonName;
import me.jone501.cryptopay.responses.BaseResponse;
import me.jone501.cryptopay.types.Currency;
import me.jone501.cryptopay.types.CurrencyList;
import me.jone501.cryptopay.types.Invoice;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class CreateInvoiceRequest extends Request<BaseResponse<Invoice>> {
    @SerializedName("currency_type")
    private String currencyType;

    @SerializedName("asset")
    private String asset;

    @SerializedName("fiat")
    private String fiat;

    @SerializedName("accepted_assets")
    private String acceptedAssets;

    @SerializedName("amount")
    private double amount;

    @SerializedName("description")
    private String description;

    @SerializedName("hidden_message")
    private String hiddenMessage;

    @SerializedName("paid_btn_name")
    private PaidButtonName paidButtonName;

    @SerializedName("paid_btn_url")
    private URL paidButtonUrl;

    @SerializedName("swap_to")
    private String swapTo;

    @SerializedName("payload")
    private String payload;

    @SerializedName("allow_comments")
    private String allowComments;

    @SerializedName("allow_anonymous")
    private String allowAnonymous;

    @SerializedName("expires_in")
    private String expiresIn;

    private CreateInvoiceRequest() {
    }

    @Override
    public String method() {
        return "createInvoice";
    }

    @Override
    public byte[] content() {
        return new Gson().toJson(this).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    protected BaseResponse<Invoice> parseJsonResponse(JsonElement json) {
        return new BaseResponse<>(json, Invoice.class);
    }

    public static Builder builder(Currency currency, double amount) {
        return new Builder(currency, amount);
    }

    public static class Builder {
        CreateInvoiceRequest request = new CreateInvoiceRequest();

        private Builder(Currency currency, double amount) {
            currency(currency).amount(amount);
        }

        public Builder currency(Currency currency) {
            if (currency.isCrypto()) {
                request.currencyType = "crypto";
                request.asset = currency.code();
            } else {
                request.currencyType = "fiat";
                request.fiat = currency.code();
            }
            return this;
        }

        public Builder acceptedAssets(Currency... acceptedAssets) {
            request.acceptedAssets = String.join(",", Arrays.stream(acceptedAssets)
                    .filter(Currency::isCrypto)
                    .map(Currency::code)
                    .toList());
            return this;
        }

        public Builder acceptedAssets(CurrencyList acceptedAssets) {
            request.acceptedAssets = String.join(",", acceptedAssets.stream()
                    .filter(Currency::isCrypto)
                    .map(Currency::code)
                    .toList());
            return this;
        }

        public Builder amount(double amount) {
            if (amount < 0)
                throw new IllegalArgumentException();
            request.amount = amount;
            return this;
        }

        public Builder description(String description) {
            if (description.length() > 1024)
                throw new IllegalArgumentException();
            request.description = description;
            return this;
        }

        public Builder hiddenMessage(String hiddenMessage) {
            if (hiddenMessage.length() > 2048)
                throw new IllegalArgumentException();
            request.hiddenMessage = hiddenMessage;
            return this;
        }

        public Builder paidButton(PaidButtonName paidButtonName, URL paidButtonUrl) {
            if (!(paidButtonUrl.getProtocol().equals("http") || paidButtonUrl.getProtocol().equals("https")))
                throw new IllegalArgumentException();
            request.paidButtonName = paidButtonName;
            request.paidButtonUrl = paidButtonUrl;
            return this;
        }

        public Builder swapTo(Currency swapTo) {
            request.swapTo = swapTo.code();
            return this;
        }

        public Builder payload(String payload) {
            request.payload = payload;
            return this;
        }

        public Builder allowComments(boolean allowComments) {
            request.allowComments = String.valueOf(allowComments);
            return this;
        }

        public Builder allowAnonymous(boolean allowAnonymous) {
            request.allowAnonymous = String.valueOf(allowAnonymous);
            return this;
        }

        public Builder expiresIn(long expiresIn) {
            if (expiresIn < 1 || expiresIn > 2678400)
                throw new IllegalArgumentException();
            request.expiresIn = String.valueOf(expiresIn);
            return this;
        }

        public CreateInvoiceRequest build() {
            return request;
        }
    }
}