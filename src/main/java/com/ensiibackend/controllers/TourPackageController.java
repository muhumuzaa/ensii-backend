package com.ensiibackend.controllers;

import com.ensiibackend.dto.BlogDTO;
import com.ensiibackend.dto.PackageDTO;
import com.ensiibackend.services.TourPackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/packages")
@RequiredArgsConstructor
public class TourPackageController {

    private TourPackageService service;

    //Create
    @PostMapping
    public ResponseEntity<PackageDTO.TourPackageResponseDTO> create(@Valid
                                                                    @RequestBody PackageDTO.CreateTourPackageDTO dto
                                                                    ){
        PackageDTO.TourPackageResponseDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/packages/"+created.id())).body(created);

    }

    //Get all (list all)
    @GetMapping
    public List<PackageDTO.TourPackageResponseDTO> list(){
        return service.list();
    }

    //Get (by slug)
    @GetMapping("/{slug}")
    public PackageDTO.TourPackageResponseDTO getBySlug(@PathVariable String slug){
        return service.getBySlug(slug);
    }

    //Update (by slug)
    @PutMapping("/{slug}")
    public ResponseEntity<PackageDTO.TourPackageResponseDTO> update(
            @PathVariable String slug,
            @Valid @RequestBody PackageDTO.UpdateTourPackageDTO dto
            ){
        PackageDTO.TourPackageResponseDTO updated = service.update(slug, dto);
        return ResponseEntity.ok(updated); //200 Ok + response body
    }

    //Delete
    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable String slug){
        service.delete(slug);
        return ResponseEntity.noContent().build(); //204 + Ok
    }

}
