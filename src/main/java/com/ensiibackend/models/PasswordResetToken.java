package com.ensiibackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="password_reset_tokens", indexes ={
        @Index(name= "idx_prt_user", columnList ="user_id"),
        @Index(name= "idx_prt_expires", columnList ="expires_at"),
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor @Builder
public class PasswordResetToken {

    @Id
    @NotBlank
    @Size(max=64)
    @Column(name="token_hash", nullable =false, length=64)
    private String tokenHash;

    @NotBlank
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable=false)
    private User userId;

    @NotBlank
    @Column(name="expires_at", nullable=false)
    private OffsetDateTime expiresAt;

    @Column(name="used_at")
    private OffsetDateTime usedAt;
}
