package com.ensiibackend.dto;

import com.ensiibackend.domain.BlogPostStatus;
import com.ensiibackend.domain.UserAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class BlogDTO {

    public record CreateBlogPostDTO(
            @NotBlank @Size(max=128) String slug,
            @NotBlank @Size(max=128) String title,
            String excerpt,
            @NotBlank String content,
            BlogPostStatus status,
            @Size(max=512) String coverImageUrl,
            @Size(max=160) String coverImageAlt,
            UserAccount author,
            List<BlogImageDTO> blogImages
    ){}

    public record BlogImageDTO(
            String imageUrl,
            String altText,
            String caption,
            Integer sortOrder
    ){}
}
