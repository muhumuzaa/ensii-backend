package com.ensiibackend.domain;

import jakarta.persistence.*;

import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="user_favorite", uniqueConstraints = {
//        @UniqueConstraint(name="uq_post_image_order", columnNames ={"blog_id", "sort_order"})
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFavorite {

    @Id
    @OneToOne( fetch=FetchType.LAZY, optional =false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    private TourPackage package;

    @Column(name="created-at", nullable=false)
    private OffsetDateTime createdAt;

    @PrePersist void onCreate(){ if (createdAt == null) createdAt = OffsetDateTime.now(); }

}
