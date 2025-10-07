package com.ensiibackend.repos;

import com.ensiibackend.domain.ItineraryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, Long> {
    Optional<ItineraryItem> findByTourPackageOrderByDayNumberAsc(Long tourPackageId);
}
