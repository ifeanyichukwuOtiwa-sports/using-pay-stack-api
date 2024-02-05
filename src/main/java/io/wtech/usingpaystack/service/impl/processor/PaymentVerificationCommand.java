package io.wtech.usingpaystack.service.impl.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wtech.usingpaystack.constant.APIConstant;
import io.wtech.usingpaystack.dto.PaymentVerificationDto;
import io.wtech.usingpaystack.response.PaymentVerificationResponse;
import io.wtech.usingpaystack.service.api.PaymentCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;

@RequiredArgsConstructor
@Component
@Slf4j
public class PaymentVerificationCommand implements PaymentCommand<PaymentVerificationDto, PaymentVerificationResponse> {
    private final RestClient restClient;
    @Override
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
