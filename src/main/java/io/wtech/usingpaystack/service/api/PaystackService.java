package io.wtech.usingpaystack.service.api;

import io.wtech.usingpaystack.dto.CreatePlanDto;
import io.wtech.usingpaystack.dto.InitializePaymentDto;
import io.wtech.usingpaystack.dto.PaymentVerificationDto;
import io.wtech.usingpaystack.response.CreatePlanResponse;
import io.wtech.usingpaystack.response.InitializePaymentResponse;
import io.wtech.usingpaystack.response.PaymentVerificationResponse;

public interface PaystackService {
    CreatePlanResponse createPlan(CreatePlanDto createPlanDto);
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(PaymentVerificationDto paymentVerificationDto);
}
