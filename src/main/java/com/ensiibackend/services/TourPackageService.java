package com.ensiibackend.services;

import com.ensiibackend.domain.ItineraryItem;
import com.ensiibackend.domain.TourPackage;
import com.ensiibackend.dto.PackageDTO.*;
import com.ensiibackend.dto.ItineraryDTO.*;
import com.ensiibackend.repos.TourPackageRepository;
import com.ensiibackend.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TourPackageService {
    private final TourPackageRepository pkgRepo;

//  ----------------------------  Create   --------------------------------------
    @Transactional
    public TourPackageResponseDTO create(CreateTourPackageDTO dto){
        if(pkgRepo.existsBySlug(dto.slug())){
            throw new IllegalArgumentException("Package slug already exisits");
        }
        //generate slug from incoming dto
        String slug = SlugifyUtils.slugify(dto.title());
        if(pkgRepo.existsBySlug(slug)){
            throw new IllegalArgumentException("Package slug already exists");
        }

        TourPackage pkg = TourPackage.builder()
                .slug(slug)
                .title(dto.title())
                .summary(dto.summary())
                .priceCents(dto.priceCents())
                .days(dto.days())
                .build();

            if(dto.itinerary() != null){
                for(var it: dto.itinerary()){
                    pkg.getItineraryItems().add(
                            ItineraryItem.builder()
                                    .tourPackage(pkg)
                                    .dayNumber(it.dayNumber())
                                    .title(it.title())
                                    .description(it.description())
                                    .startLocation(it.startLocation())
                                    .endLocation(it.endLocation())
                                    .build()
                    );
                }
            }
            TourPackage saved = pkgRepo.save(pkg);
            return toResponse(saved);
    }

    //  ----------------------------  Find all   --------------------------------------
    @Transactional(readOnly =true)
    public List<TourPackageResponseDTO> list(){
        return pkgRepo.findAll().stream().map(this::toResponse).toList();
    }

    //  ----------------------------  get By slug   --------------------------------------
    @Transactional(readOnly = true)
    public TourPackageResponseDTO getBySlug(String slug){
        return pkgRepo.findBySlug(slug).map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Package with this slug not found."));
    }

    //  ----------------------------  Delete   --------------------------------------
    public void delete(String slug){
        TourPackage tp = pkgRepo.findBySlug(slug).orElseThrow(() -> new NoSuchElementException("Package not found"));
        pkgRepo.delete(tp);
    }

    public TourPackageResponseDTO update(String slug, UpdateTourPackageDTO dto){
        TourPackage pk = pkgRepo.findBySlug(slug).orElseThrow(() ->
                new NoSuchElementException("Package not found"));

        //update slug if title changes
        if(!pk.getTitle().equals(dto.title())){
            String newSlug = SlugifyUtils.slugify(dto.title());
            if(pkgRepo.existsBySlug(newSlug)){
                throw new IllegalArgumentException("Another package already uses this slug.");
            }
            pk.setSlug(newSlug);
        }
        pk.setTitle(dto.title());
        pk.setSummary(dto.summary());
        pk.setPriceCents(dto.priceCents());
        pk.setDays(dto.days());

        //replace itinerary items
        pk.getItineraryItems().clear();
        if(dto.itinerary() != null){
            for (var it: dto.itinerary()){
                pk.getItineraryItems().add(
                        ItineraryItem.builder()
                                .tourPackage(pk)
                                .dayNumber(it.dayNumber())
                                .title(it.title())
                                .description(it.description())
                                .startLocation(it.startLocation())
                                .endLocation(it.endLocation())
                                .build()
                );
            }
        }
        return toResponse(pk);
    }

    //  ----------------------------  RESPONSE   --------------------------------------
    public TourPackageResponseDTO toResponse(TourPackage p){
        var items = p.getItineraryItems().stream()
                .sorted(Comparator.comparing(ItineraryItem::getDayNumber))
                .map(i -> new ItineraryItemResponseDTO(
                        i.getDayNumber(),
                        i.getTitle(),
                        i.getDescription(),
                        i.getStartLocation(),
                        i.getEndLocation()
                )).toList();
        return new TourPackageResponseDTO(
                p.getId(),
                p.getSlug(),
                p.getTitle(),
                p.getSummary(),
                p.getPriceCents(),
                p.getDays(),
                items
                );
    }


}
