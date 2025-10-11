package com.ensiibackend.dto;

import com.ensiibackend.dto.ItineraryDTO.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public class PackageDTO {

    public record CreateTourPackageDTO(
            @NotBlank @Size(max=128) String slug,
            @NotBlank @Size(max=128) String title,
            String summary,
            @NotBlank @Min(0) Integer priceCents,
            @NotBlank @Min(1) Integer days,
            List<@Valid CreateItineraryItemDTO> itinerary
            ){}

    public record UpdateTourPackageDTO(
            @NotBlank @Size(max=128) String slug,
            @NotBlank @Size(max=128) String title,
            String summary,
            @NotBlank @Min(0) Integer priceCents,
            @NotBlank @Min(1) Integer days,
            List<@Valid CreateItineraryItemDTO> itinerary
    ){}

    public record TourPackageResponseDTO(
            Long id,
            String slug,
            String title,
            String summary,
            Integer priceCents,
            Integer days,
            List<ItineraryItemResponseDTO> itinerary
            ){}
}
