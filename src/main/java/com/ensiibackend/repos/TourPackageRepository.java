package com.ensiibackend.repos;

import com.ensiibackend.domain.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourPackageRepository extends JpaRepository<TourPackage, Long> {
    Optional<TourPackage> findBySlug(String slug);
    boolean existsBySlug(String slug);
}
