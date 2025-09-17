package com.ensiibackend.models;

import jakarta.persistence.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="itinerary_items", indexes ={
        @Index(name="idx_itinerary_pkg_day", columnList= "tour_package_id, day_number")
}, uniqueConstraints = @UniqueConstraint(name ="uq_package_id_day", columnNames ={"tour_package_id", "day_number"}))
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItineraryItem {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="tour_package_id", nullable=false)
    private TourPackage tourPackage;

    @NotBlank @Min(1)
    @Column(name="day_number", nullable=false)
    private Integer dayNumber;

    @NotBlank @Size(max=160)
    @Column(nullable=false)
    private String title;

    @Column(columnDefinition="text")
    private String description;

    @Size(max=160)
    @Column(name ="start_location", length=160)
    private String startLocation;

    @Size(max=160)
    @Column(name ="end_location", length=160)
    private String endLocation;

    @NotBlank
    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    @NotBlank
    @Column(name="updated_at", nullable=false)
    private OffsetDateTime updatedAt;

    @PrePersist void onCreate(){createdAt = updatedAt = OffsetDateTime.now();}
    @PreUpdate void onUpdate(){updatedAt = OffsetDateTime.now();}
}
