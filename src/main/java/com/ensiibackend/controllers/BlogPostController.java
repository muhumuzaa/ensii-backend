package com.ensiibackend.controllers;

import com.ensiibackend.domain.BlogPost;
import com.ensiibackend.dto.BlogDTO;
import com.ensiibackend.services.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogPostController {
    private final BlogPostService service;


    //Create
    @PostMapping
    public ResponseEntity<BlogDTO.BlogPostResponseDTO> create(@Valid @RequestBody BlogDTO.CreateBlogPostDTO dto){
        BlogDTO.BlogPostResponseDTO created = service.create(dto);
        return ResponseEntity.created(URI.create("/api/blogs/"+created.slug())).body(created);
    }

    //Get Blogs (Page)
    @GetMapping
    public Page<BlogDTO.BlogPostResponseDTO> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return service.listPublished(PageRequest.of(page, size));
    }

    //Get 1 blog (by slug)
    @GetMapping("/{slug}")
    public Optional<BlogDTO.BlogPostResponseDTO> getBySlug(@PathVariable String slug){
        return service.getBySlug(slug);
    }

    //Update (By slug)
    @PutMapping("/{slug}")
    public ResponseEntity<BlogDTO.BlogPostResponseDTO> update(@PathVariable String slug, @Valid @RequestBody BlogDTO.UpdateBlogPostDTO dto){
        BlogDTO.BlogPostResponseDTO updated = service.update(slug, dto);
        return ResponseEntity.ok(updated); //200 OK + json body
    }

    //Delete
    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> delete(@PathVariable String slug){
        service.delete(slug);
        return ResponseEntity.noContent().build(); //204 + No content
    }
}
