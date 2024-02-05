package io.wtech.usingpaystack.dto;

import java.util.UUID;

public record PaymentVerificationDto(
        String reference,
        String plan,
        UUID userUuid
) {
}
