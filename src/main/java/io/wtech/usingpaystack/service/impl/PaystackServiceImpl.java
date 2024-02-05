package io.wtech.usingpaystack.service.impl;

import io.wtech.usingpaystack.dto.CreatePlanDto;
import io.wtech.usingpaystack.dto.InitializePaymentDto;
import io.wtech.usingpaystack.dto.PaymentVerificationDto;
import io.wtech.usingpaystack.model.AppUser;
import io.wtech.usingpaystack.model.PaystackPayment;
import io.wtech.usingpaystack.model.PricingPlanType;
import io.wtech.usingpaystack.repository.PaystackPaymentRepository;
import io.wtech.usingpaystack.response.CreatePlanResponse;
import io.wtech.usingpaystack.response.InitializePaymentResponse;
import io.wtech.usingpaystack.response.PaymentVerificationResponse;
import io.wtech.usingpaystack.service.api.AppUserService;
import io.wtech.usingpaystack.service.api.PaystackService;
import io.wtech.usingpaystack.service.impl.processor.CreatePlanCommand;
import io.wtech.usingpaystack.service.impl.processor.InitializePaymentCommand;
import io.wtech.usingpaystack.service.impl.processor.PaymentVerificationCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaystackServiceImpl implements PaystackService {
    private final AppUserService appUserService;
    private final PaystackPaymentRepository paystackPaymentRepository;
    private final CreatePlanCommand createPlanCommand;

    private final InitializePaymentCommand initializePaymentCommand;
    private final PaymentVerificationCommand paymentVerificationCommand;

    @Override
    public CreatePlanResponse createPlan(final CreatePlanDto createPlanDto) {
        try {
            return createPlanCommand.execute(createPlanDto);
        } catch (ClassCastException e) {
            return null;
        }
    }

    @Override
    public InitializePaymentResponse initializePayment(final InitializePaymentDto initializePaymentDto) {
        return initializePaymentCommand.execute(initializePaymentDto);
    }

    @Override
    public PaymentVerificationResponse paymentVerification(PaymentVerificationDto dto) {
        try {
            final PaymentVerificationResponse paymentVerificationResponse = paymentVerificationCommand.execute(dto);
            Optional.ofNullable(paymentVerificationResponse)
                    .ifPresent(p -> paystackPaymentRepository.save(mapToEntity(p, dto.userUuid(), dto.plan())));
            return paymentVerificationResponse;
        } catch (ClassCastException e) {
            return null;
        }
    }

    private PaystackPayment mapToEntity(final PaymentVerificationResponse r, final UUID id, final String plan) {
        final AppUser appUser = appUserService.getUserById(id);
        PricingPlanType pricingPlanType = PricingPlanType.fromValue(plan.toUpperCase());

        return PaystackPayment.builder()
                .user(appUser)
                .reference(r.getData().getReference())
                .amount(r.getData().getAmount())
                .gatewayResponse(r.getData().getGatewayResponse())
                .paidAt(r.getData().getPaidAt())
                .createdAt(r.getData().getCreatedAt())
                .channel(r.getData().getChannel())
                .currency(r.getData().getCurrency())
                .ipAddress(r.getData().getIpAddress())
                .pricingPlanType(pricingPlanType.getValue())
                .createdOn(LocalDateTime.now())
                .build();
    }
}
