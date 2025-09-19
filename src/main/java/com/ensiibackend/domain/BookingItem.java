package com.ensiibackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity
@Table(name="booking_items")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch =FetchType.LAZY, optional=false)
    @JoinColumn(name="booking_id", nullable=false)
    private Booking booking;

    @ManyToOne(fetch =FetchType.LAZY, optional=false)
    @JoinColumn(name="tour_package_id", nullable=false)
    private TourPackage tourPackage;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="package_departure_id", nullable=false)
    private PackageDeparture departure; // may be null if open-date booking

    @Column(nullable=false) @Min(1)
    private Integer quantity;

    @Column(name="unit_price_cents") @Min(0)
    private Integer unitPriceCents;

    @Column(name="line_total_cents") @Min(0)
    private Integer lineTotalCents;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    @PrePersist void onCreate(){ createdAt = updatedAt = OffsetDateTime.now(); }
    @PreUpdate  void onUpdate(){ updatedAt = OffsetDateTime.now(); }

}
