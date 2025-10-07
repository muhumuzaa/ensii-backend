package com.ensiibackend.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserAccount {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable =false, unique =true, length =100)
    @Size(max= 100) @Email @NotBlank
    private String email;

    @Column(name="password_hash", nullable =false, length =255)
    @Size(max= 255) @NotBlank
    private String passwordHash;


    @Size(max= 100)
    @Column(nullable =false, name="first_name", length =100)
    private String firstName;

    @Column(nullable =false, name="last_name", length =100)
    @Size(max= 100)
    private String lastName;

    @Size(max=20)
    private String phone;

    @Column( name="address_line1", length=160)
    @Size(max= 160)
    private String addressLine1;

    @Column( name="address_line2", length=160)
    @Size(max= 160)
    private String addressLine2;

    @Size(max= 160)
    @Column(length =160)
    private String city;

    @Column( name="state_province", length=160)
    @Size(max= 160)
    private String stateProvince;

    @Column( name="postal_code", length=160)
    @Size(max= 160)
    private String postal_code;

    @Column( name="country_code", length=2)
    @Size(max= 2)
    private String country_code;

    @Enumerated(EnumType.STRING)
    @Size(max=20)
    @Column(nullable=false, length =20)
    private Role role = Role.CUSTOMER;

    @Column(name="is_verified", nullable=false)
    private boolean isVerified =false;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    @Column(name="updated_at", nullable=false)
    private OffsetDateTime updatedAt;
}
