package io.wtech.usingpaystack.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "app_user")
public class AppUser {

    @Id
    @Column(name = "id", nullable = false)
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "created_on", updatable = false, nullable = false)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @Builder.Default
    Set<PaystackPayment> payments = new HashSet<>();
}
