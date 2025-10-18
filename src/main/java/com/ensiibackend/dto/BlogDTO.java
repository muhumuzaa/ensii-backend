package com.ensiibackend.dto;

import com.ensiibackend.domain.BlogPostStatus;
import com.ensiibackend.domain.UserAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
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
            UserAccount authorUserId,
            List<BlogImageDTO> blogImages
    ){}

    public record BlogPostResponseDTO(
//            Long id,
            String slug,
            String title,
            String excerpt,
            String content,
            BlogPostStatus status,
            String coverImageUrl,
            String coverImageAlt,
            String authorName,
            List<BlogImageResponseDTO> images,
            OffsetDateTime publishedAt,
            OffsetDateTime createdAt,
            OffsetDateTime updatedAt
    ){}

    public record UpdateBlogPostDTO(
            @NotBlank @Size(max=128) String title,
            String excerpt,
            @NotBlank String content,
            BlogPostStatus status,
            @Size(max=512) String coverImageUrl,
            @Size(max=160) String coverImageAlt,
            UserAccount authorUserId,
            List<BlogImageDTO> blogImages
    ){}

    public record BlogImageDTO(
            @NotBlank @Size(max=512) String imageUrl,
            @NotBlank @Size(max=160) String altText,
            String caption,
            Integer sortOrder
    ){}

    public record BlogImageResponseDTO(
            String imageUrl,
            String altText,
            String caption,
            Integer sortOrder
    ) {}
}
