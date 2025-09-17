package com.ensiibackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tour_packages")
@Getter
@Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TourPackage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique = true, length =128)
    @NotBlank @Size(max=128)
    private String slug;

    @Column(nullable=false, length =128)
    @NotBlank @Size(max=128)
    private String title;

    @Column(columnDefinition="text")
    private String summary;

    @NotBlank @Min(0)
    @Column(name="price_cents", nullable=false)
    private Integer priceCents;

    @NotBlank @Min(1)
    @Column(nullable=false)
    private Integer days;

    @NotBlank
    @Column(name="created_at", nullable =false)
    private OffsetDateTime createdAt;

    @NotBlank
    @Column(name="updated_at", nullable =false)
    private OffsetDateTime updatedAt;

    @Builder.Default
    @OneToMany(mappedBy="tourPackage", fetch= FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval =true)
    private List<ItineraryItem> itineraryItems = new ArrayList<>();

    @PrePersist void onCreate(){createdAt = updatedAt = OffsetDateTime.now();}
    @PreUpdate void onUpdate(){updatedAt = OffsetDateTime.now();}
}
