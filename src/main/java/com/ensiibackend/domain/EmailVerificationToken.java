package com.ensiibackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="email_verification_tokens", indexes={
        @Index(name= "idx_evt_user", columnList = "user_id"),
        @Index(name="idx_evt_expires", columnList= "expires_at")
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailVerificationToken {

    @Id @Size(max=64)
    @Column(name="token_hash", length=64)
    private String tokenHash;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Column(name="expires_at", nullable=false)
    private OffsetDateTime expiresAt;

    @Column(name="used_at")
    private OffsetDateTime usedAt;
}
