package ru.jone501.cryptopay;

import ru.jone501.cryptopay.polling.PollingConfiguration;
import ru.jone501.cryptopay.polling.PollingManager;
import ru.jone501.cryptopay.requests.*;
import ru.jone501.cryptopay.responses.BaseResponse;
import ru.jone501.cryptopay.responses.ResponseHandler;
import ru.jone501.cryptopay.types.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class CryptoApp {
    private final String host, token;
    private final PollingManager pollingManager;

    public String host() {
        return host;
    }

    public String token() {
        return token;
    }

    public PollingManager pollingManager() {
        return pollingManager;
    }

    private final HttpClient httpClient = HttpClient.newHttpClient();

    protected CryptoApp(String host, String token, PollingConfiguration configuration) {
        this.host = host;
        this.token = token;
        pollingManager = new PollingManager(this, configuration);
    }

    public CryptoApp(String token, PollingConfiguration configuration) {
        this("pay.crypt.bot/api/", token, configuration);
    }

    public CryptoApp(String token) {
        this(token, new PollingConfiguration());
    }

    public void loadCurrencies() throws CryptoPayApiException {
        Currencies.init(getCurrencies());
    }

    public void loadCurrenciesAsync() {
        getCurrenciesAsync(ResponseHandler.create(Currencies::init));
    }

    public <T> T request(Request<BaseResponse<T>> request) throws CryptoPayApiException {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URL("https://" + host + request.method()).toURI())
                    .POST(HttpRequest.BodyPublishers.ofByteArray(request.content()))
                    .header("Crypto-Pay-API-Token", token)
                    .header("Content-Type",	"application/json")
                    .build();
            HttpResponse<byte[]> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            BaseResponse<T> responseObj = request.parseResponse(response.body());
            if (!responseObj.ok())
                throw new CryptoPayApiException(responseObj.error());
            return responseObj.result();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void requestAsync(Request<BaseResponse<T>> request, ResponseHandler<T> handler) {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URL("https://" + host + request.method()).toURI())
                    .POST(HttpRequest.BodyPublishers.ofByteArray(request.content()))
                    .header("Crypto-Pay-API-Token", token)
                    .header("Content-Type",	"application/json")
                    .build();
            CompletableFuture<HttpResponse<byte[]>> response = httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            response.thenApply(r -> request.parseResponse(r.body())).thenAccept(r -> {
                if (r.ok()) handler.success(r.result());
                else handler.error(r.error());
            });
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public AppInfo getMe() throws CryptoPayApiException {
        return request(new GetMeRequest());
    }

    public Invoice createInvoice(CreateInvoiceRequest.Builder builder) throws CryptoPayApiException {
        return request(builder.build());
    }

    public Boolean deleteInvoice(long invoiceId) throws CryptoPayApiException {
        return request(new DeleteInvoiceRequest(invoiceId));
    }

    public Check createCheck(CreateCheckRequest.Builder builder) throws CryptoPayApiException {
        return request(builder.build());
    }

    public Boolean deleteCheck(long checkId) throws CryptoPayApiException {
        return request(new DeleteCheckRequest(checkId));
    }

    public Transfer transfer(TransferRequest.Builder builder) throws CryptoPayApiException {
        return request(builder.build());
    }

    public Invoice getInvoice(long invoiceId) throws CryptoPayApiException {
        return request(new GetInvoiceRequest(invoiceId));
    }

    public Invoice[] getInvoices(GetInvoicesRequest.Builder builder) throws CryptoPayApiException {
        return request(builder.build());
    }

    public Check getCheck(long checkId) throws CryptoPayApiException {
        return request(new GetCheckRequest(checkId));
    }

    public Check[] getChecks(GetChecksRequest.Builder builder) throws CryptoPayApiException {
        return request(builder.build());
    }

    public Transfer[] getTransfers(GetTransfersRequest.Builder builder) throws CryptoPayApiException {
        return request(builder.build());
    }

    public Balance[] getBalance() throws CryptoPayApiException {
        return request(new GetBalanceRequest());
    }

    public ExchangeRateList getExchangeRates() throws CryptoPayApiException {
        return request(new GetExchangeRatesRequest());
    }

    public CurrencyList getCurrencies() throws CryptoPayApiException {
        CurrencyList currencies = request(new GetCurrenciesRequest());
        Currencies.init(currencies);
        return currencies;
    }

    public AppStats getStats(GetStatsRequest.Builder builder) throws CryptoPayApiException {
        return request(builder.build());
    }

    public void getMeAsync(ResponseHandler<AppInfo> handler) {
        requestAsync(new GetMeRequest(), handler);
    }

    public void createInvoiceAsync(CreateInvoiceRequest.Builder builder, ResponseHandler<Invoice> handler) {
        requestAsync(builder.build(), handler);
    }

    public void deleteInvoiceAsync(long invoiceId, ResponseHandler<Boolean> handler) {
        requestAsync(new DeleteInvoiceRequest(invoiceId), handler);
    }

    public void createCheckAsync(CreateCheckRequest.Builder builder, ResponseHandler<Check> handler) {
        requestAsync(builder.build(), handler);
    }

    public void deleteCheckAsync(long checkId, ResponseHandler<Boolean> handler) {
        requestAsync(new DeleteCheckRequest(checkId), handler);
    }

    public void transferAsync(TransferRequest.Builder builder, ResponseHandler<Transfer> handler) {
        requestAsync(builder.build(), handler);
    }

    public void getInvoiceAsync(long invoiceId, ResponseHandler<Invoice> handler) {
        requestAsync(new GetInvoiceRequest(invoiceId), handler);
    }

    public void getInvoicesAsync(GetInvoicesRequest.Builder builder, ResponseHandler<Invoice[]> handler) {
        requestAsync(builder.build(), handler);
    }

    public void getCheckAsync(long checkId, ResponseHandler<Check> handler) {
        requestAsync(new GetCheckRequest(checkId), handler);
    }

    public void getChecksAsync(GetChecksRequest.Builder builder, ResponseHandler<Check[]> handler) {
        requestAsync(builder.build(), handler);
    }

    public void getTransfersAsync(GetTransfersRequest.Builder builder, ResponseHandler<Transfer[]> handler) {
        requestAsync(builder.build(), handler);
    }

    public void getBalanceAsync(ResponseHandler<Balance[]> handler) {
        requestAsync(new GetBalanceRequest(), handler);
    }

    public void getExchangeRatesAsync(ResponseHandler<ExchangeRateList> handler) {
        requestAsync(new GetExchangeRatesRequest(), handler);
    }

    public void getCurrenciesAsync(ResponseHandler<CurrencyList> handler) {
        requestAsync(new GetCurrenciesRequest(), ResponseHandler.create(r -> {
            Currencies.init(r);
            handler.success(r);
        }, handler::error));
    }

    public void getStatsAsync(GetStatsRequest.Builder builder, ResponseHandler<AppStats> handler) {
        requestAsync(builder.build(), handler);
    }
}
