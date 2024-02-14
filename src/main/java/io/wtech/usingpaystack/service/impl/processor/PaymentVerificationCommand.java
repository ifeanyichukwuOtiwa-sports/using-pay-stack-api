package io.wtech.usingpaystack.service.impl.processor;

import io.wtech.usingpaystack.constant.APIConstant;
import io.wtech.usingpaystack.dto.PaymentVerificationDto;
import io.wtech.usingpaystack.response.PaymentVerificationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
@Slf4j
public class PaymentVerificationCommand {
    private final RestClient restClient;

    public PaymentVerificationResponse execute(final PaymentVerificationDto request) {
        PaymentVerificationResponse paymentVerificationResponse = null;
        try {
            paymentVerificationResponse = restClient.get()
                    .uri(APIConstant.PAYSTACK_VERIFY + request.reference())
                    .retrieve()
                    .body(PaymentVerificationResponse.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return paymentVerificationResponse;
    }
}
