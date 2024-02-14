package io.wtech.usingpaystack.controller;

import io.wtech.usingpaystack.dto.CreatePlanDto;
import io.wtech.usingpaystack.dto.InitializePaymentDto;
import io.wtech.usingpaystack.dto.PaymentVerificationDto;
import io.wtech.usingpaystack.exception.WrongParamsException;
import io.wtech.usingpaystack.response.CreatePlanResponse;
import io.wtech.usingpaystack.response.InitializePaymentResponse;
import io.wtech.usingpaystack.response.PaymentVerificationResponse;
import io.wtech.usingpaystack.service.api.PaystackService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

/**
 *
 */
@RestController
@RequestMapping(path = "/api/v1/payment")
@RequiredArgsConstructor
public class PaystackController {

    private final PaystackService paystackService;

    @PostMapping("/createplan")
    public CreatePlanResponse createPlan(@Validated @RequestBody CreatePlanDto createPlanDto) {
        return paystackService.createPlan(createPlanDto);
    }

    @PostMapping("/initializepayment")
    public InitializePaymentResponse initializePayment(@Validated @RequestBody InitializePaymentDto initializePaymentDto) {
        return paystackService.initializePayment(initializePaymentDto);
    }

    @GetMapping("/verifypayment/{reference}/{plan}/{id}")
    public PaymentVerificationResponse paymentVerification(@PathVariable(value = "reference") String reference,
                                                           @PathVariable(value = "plan") String plan,
                                                           @PathVariable(value = "id") UUID id) {
        validateParams(reference, plan);
        return paystackService.paymentVerification(new PaymentVerificationDto(reference, plan, id));
    }

    private void validateParams(final String reference, final String plan) {
        if (
                Objects.isNull(reference)
                        || Objects.isNull(plan)
                        || !StringUtils.hasText(reference)
                        || !StringUtils.hasText(plan)
                        || reference.equals("%20")
                        || plan.equals("%20")


        ) {
            throw new WrongParamsException("reference, plan and id must be provided in path");
        }
    }
}
