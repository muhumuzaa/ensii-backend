package com.ensiibackend.dto;

import com.ensiibackend.domain.TourPackage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ItineraryDTO {
    public record CreateItineraryItemDTO(
            @NotBlank @Min(1) Integer dayNumber,
            @NotBlank @Size(max=160) String title,
            String description,
            @Size(max=160) String startLocation,
            @Size(max=160) String endLocation
    ){}

    public record ItineraryItemResponseDTO(
            Integer dayNumber,
            String title,
            String description,
            String startLocation,
            String endLocation
    ){}
}
