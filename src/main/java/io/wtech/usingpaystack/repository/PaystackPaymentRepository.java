package io.wtech.usingpaystack.repository;

import io.wtech.usingpaystack.model.PaystackPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaystackPaymentRepository extends JpaRepository<PaystackPayment, UUID> {
}
