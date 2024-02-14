package io.wtech.usingpaystack.service.impl.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wtech.usingpaystack.constant.APIConstant;
import io.wtech.usingpaystack.dto.InitializePaymentDto;
import io.wtech.usingpaystack.response.InitializePaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
@Component
@Slf4j
public class InitializePaymentCommand {
    private final RestClient restClient;
    private final ObjectMapper mapper;

    public InitializePaymentResponse execute(final InitializePaymentDto request) {
        InitializePaymentResponse initializePaymentResponse = null;
        try {
            final String postingString = mapper.writeValueAsString(request);
            initializePaymentResponse = restClient.post()
                    .uri(APIConstant.PAYSTACK_INITIALIZE_PAY)
                    .body(postingString)
                    .retrieve()
                    .body(InitializePaymentResponse.class);
        } catch (Exception e) {
            log.error("Error initializing payment", e);
        }
        return initializePaymentResponse;
    }
}
