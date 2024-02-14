package io.wtech.usingpaystack.service.api.processor;

import io.wtech.usingpaystack.dto.PaymentVerificationDto;
import io.wtech.usingpaystack.response.PaymentVerificationResponse;

public interface PaymentProcessor<T, R> {

    R process(String type, T payload);
}
