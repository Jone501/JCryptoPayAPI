package ru.jone501.cryptopay.responses;

import ru.jone501.cryptopay.types.Error;

import java.util.function.Consumer;

public class ResponseHandler<R> {
    private final Consumer<R> successHandler;
    private final Consumer<Error> errorHandler;

    private ResponseHandler(Consumer<R> successHandler, Consumer<Error> errorHandler) {
        this.successHandler = successHandler;
        this.errorHandler = errorHandler;
    }

    public void success(R result) {
        successHandler.accept(result);
    }

    public void error(Error error) {
        errorHandler.accept(error);
    }

    public static <R> ResponseHandler<R> create(Consumer<R> successHandler, Consumer<Error> errorHandler) {
        return new ResponseHandler<>(successHandler, errorHandler);
    }

    public static <R> ResponseHandler<R> create(Consumer<R> successHandler) {
        return new ResponseHandler<>(successHandler, x -> {});
    }

    public static <R> ResponseHandler<R> create() {
        return new ResponseHandler<>(x -> {}, x -> {});
    }
}