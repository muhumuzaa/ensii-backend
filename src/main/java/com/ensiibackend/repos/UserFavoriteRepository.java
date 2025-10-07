package com.ensiibackend.repos;

import com.ensiibackend.domain.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
    boolean existsByUserAccountIdAndTourPackageId(Long userAccountId, Long tourPackageId);
    Optional<UserFavorite> findByUserIdAndTourPackageId(Long userId, Long tourPackageId);
    List<UserFavorite> findByUserId(Long userId);
}
