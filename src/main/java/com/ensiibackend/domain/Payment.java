package com.ensiibackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.OffsetDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name="payments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(mappedBy="booking", fetch= LAZY, optional=false)
    @JoinColumn(name="booking_id", nullable=false)
    private Booking booking;

    @NotBlank @Size(max=100)
    @Column(nullable=false, length =100)
    private String provider;

    @NotBlank @Size(max=128)
    @Column(name="provider_payment_intent_id", nullable=false, length =128)
    private String providerIntentId;


    @Min(0)
    @Column(name="amount_cents", nullable=false)
    private Integer amountCents;

    @NotBlank @Size(max=3)
    @Column(nullable=false, length=3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private PaymentStatus status = PaymentStatus.REQUIRES_ACTION;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    @PrePersist void onCreate(){ createdAt = updatedAt = OffsetDateTime.now(); }
    @PreUpdate  void onUpdate(){ updatedAt = OffsetDateTime.now(); }
}
