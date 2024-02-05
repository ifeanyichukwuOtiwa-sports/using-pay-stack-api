package io.wtech.usingpaystack.service.api;

public interface PaymentCommand<T, R> {
    R execute(T request);
}
