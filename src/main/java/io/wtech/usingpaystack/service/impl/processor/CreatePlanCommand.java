package io.wtech.usingpaystack.service.impl.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wtech.usingpaystack.constant.APIConstant;
import io.wtech.usingpaystack.dto.CreatePlanDto;
import io.wtech.usingpaystack.response.CreatePlanResponse;
import io.wtech.usingpaystack.service.api.PaymentCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class CreatePlanCommand implements PaymentCommand<CreatePlanDto, CreatePlanResponse> {
    private final RestClient restClient;
    private final ObjectMapper mapper;
    @Override
    public CreatePlanResponse execute(final CreatePlanDto request) {
        io.wtech.usingpaystack.response.CreatePlanResponse createPlanResponse = null;

        try {
            final String postingString = mapper.writeValueAsString(request);

            createPlanResponse = restClient.post()
                    .uri(APIConstant.PAYSTACK_PLAN)
                    .body(postingString)
                    .retrieve()
                    .onStatus(
                            t -> !t.isSameCodeAs(HttpStatusCode.valueOf(APIConstant.STATUS_CODE_CREATED)),
                            (r, c) -> log.error("Error creating: {}", c.getStatusCode())
                    )
                    .body(CreatePlanResponse.class);
        } catch(Exception ex) {
            log.error("Error creating plan", ex);
        }
        Optional.ofNullable(createPlanResponse)
                .ifPresent(planResponse -> {
                    if(Boolean.FALSE.equals(planResponse.getStatus())) {
                        log.error("Error creating plan: {}", planResponse.getMessage());
                        throw new RuntimeException(planResponse.getMessage());
                    }
                });
        return createPlanResponse;
    }
}
