package io.wtech.usingpaystack.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "paystack_payment")
@EqualsAndHashCode(of = "id")
public class PaystackPayment {

    @Id
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    @Column(name = "reference")
    private String reference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "gateway_response")
    private String gatewayResponse;

    @Column(name = "paid_at")
    private String paidAt;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "channel")
    private String channel;

    @Column(name = "currency")
    private String currency;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "pricing_plan_type", nullable = false)
    @Builder.Default
    private String pricingPlanType = PricingPlanType.BASIC.getValue();

    @Column(name = "created_on", updatable = false, nullable = false)
    private LocalDateTime createdOn;
}
