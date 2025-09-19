package com.ensiibackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name="package_departures", indexes= {
        @Index(name="idx_departures_pkg_date", columnList="tour_package_id, start_date")
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageDeparture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="tour_package_id", nullable=false)
    private TourPackage tourPackage;


    @Column(name ="start_date", nullable=false)
    private LocalDate startDate;

    @Column(name ="end_date", nullable=false)
    private LocalDate endDate;

    @Min(0)
    @Column(name="seats_total")
    private Integer seatsTotal;

    @Min(0)
    @Column(name="seats_available")
    private Integer seatsAvailable;

    @Min(0)
    @Column(name="price_cents_override")
    private Integer priceCentsOverride;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
    @PrePersist void onCreate(){ createdAt = updatedAt = OffsetDateTime.now(); }
    @PreUpdate  void onUpdate(){ updatedAt = OffsetDateTime.now(); }

    @AssertTrue(message = "seatsAvailable must be null or between 0 and seatsTotal when seatsTotal is set")
    private boolean isSeatAvailabilityValid(){
        if(seatsAvailable ==null) return true;
        if(seatsTotal ==null) return false;
        return seatsAvailable >= 0 && seatsAvailable <=seatsTotal;
    }

    @AssertTrue(message ="endDate must be on or after startDate")
    private boolean isDateRangeValid(){
        return endDate ==null || startDate==null || !endDate.isBefore(startDate);
    }

}
