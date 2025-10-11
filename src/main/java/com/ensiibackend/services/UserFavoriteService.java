package com.ensiibackend.services;

import com.ensiibackend.domain.TourPackage;
import com.ensiibackend.domain.UserFavorite;
import com.ensiibackend.repos.TourPackageRepository;
import com.ensiibackend.repos.UserFavoriteRepository;
import com.ensiibackend.repos.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserFavoriteService {

    private final UserFavoriteRepository favRepo;
    private final TourPackageRepository pkgRepo;
    private final UserRepository userRepo;

    @Transactional
    public void addFavorite(Long userId, String packageSlug){
        Long pkgId = pkgRepo.findBySlug(packageSlug)
                .map(TourPackage::getId)
                .orElseThrow(() ->new NoSuchElementException("Package you are favoriting not found."));

        if(favRepo.existsByUserAccountIdAndTourPackageId(userId, pkgId)) return;

        var fav = UserFavorite.builder()
                .userAccount(userRepo.getReferenceById(userId))
                .tourPackage(pkgRepo.getReferenceById(pkgId))
                .createdAt(OffsetDateTime.now())
                .build();
        favRepo.save(fav);
    }

    @Transactional
    public void removeFavorite(Long userId, String packageSlug){
        Long pkgId = pkgRepo.findBySlug(packageSlug)
                .map(TourPackage::getId)
                .orElseThrow(() -> new NoSuchElementException("Package not found"));

        favRepo.findByUserIdAndTourPackageId(userId, pkgId)
                .ifPresent(favRepo::delete);
    }

    @Transactional(readOnly = true)
    public List<String> listFavoriteSlugs(Long userId){
        return favRepo.findByUserId(userId).stream()
                .map(f -> f.getTourPackage().getSlug()).toList();
    }
}
