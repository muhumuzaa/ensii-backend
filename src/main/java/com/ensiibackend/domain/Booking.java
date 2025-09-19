package com.ensiibackend.domain;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="bookings")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional=false, cascade = CascadeType.ALL)
    @Column(name="user_id", nullable =false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable=false, length =24)
    private BookingStatus bookingStatus = BookingStatus.DRAFT;

    @NotBlank
    @Column(nullable=false, length =3)
    private String currency ="CAD";

    @Min(0)
    @Column(name="total_cents", nullable=false)
    private Integer totalCents =0;

    @Column(columnDefinition="text")
    private String notes;

    @Builder.Default
    @OneToMany(mappedBy="booking", cascade =CascadeType.ALL, orphanRemoval = true)
    private List<BookingItem> bookingItems = new ArrayList<>();

    @OneToMany(mappedBy ="booking_id", cascade =CascadeType.ALL, orphanRemoval =true)
    private List<Payment> payments = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    @PrePersist void onCreate(){ createdAt = updatedAt = OffsetDateTime.now(); }
    @PreUpdate  void onUpdate(){ updatedAt = OffsetDateTime.now(); }

}
