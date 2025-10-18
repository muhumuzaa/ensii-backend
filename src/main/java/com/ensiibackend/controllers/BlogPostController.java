package com.ensiibackend.controllers;

import com.ensiibackend.domain.BlogPost;
import com.ensiibackend.dto.BlogDTO;
import com.ensiibackend.services.BlogPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogPostController {
    private final BlogPostService blogPostService;


    @PostMapping
    public BlogPost create(@Valid @RequestBody BlogDTO.CreateBlogPostDTO dto){
        return blogPostService.create(dto);
    }

    @PutMapping("/{slug}")
    public BlogPost update(@PathVariable String slug, @Valid @RequestBody BlogDTO.UpdateBlogPostDTO dto){
        return blogPostService.update(slug, dto);
    }

    @GetMapping
    public Page<BlogPost> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return blogPostService.listPublished(PageRequest.of(page, size));
    }

    @GetMapping("/{slug}")
    public Optional<BlogPost> getBySlug(@PathVariable String slug){
        return blogPostService.getBySlug(slug);
    }
}
