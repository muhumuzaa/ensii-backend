package com.ensiibackend.domain;

import jakarta.persistence.*;

import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="user_favorite", uniqueConstraints = {
        @UniqueConstraint(name="uq_user_favorite", columnNames ={"user_id", "tour_package_id"})
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount userAccount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tour_pacakge_id", nullable = false)
    private TourPackage tourPackage;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) createdAt = OffsetDateTime.now();
    }
}