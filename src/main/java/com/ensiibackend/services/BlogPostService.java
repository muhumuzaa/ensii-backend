package com.ensiibackend.services;

import com.ensiibackend.domain.BlogImage;
import com.ensiibackend.domain.BlogPost;
import com.ensiibackend.domain.BlogPostStatus;
import com.ensiibackend.dto.BlogDTO.*;
import com.ensiibackend.repos.BlogPostRepository;
import com.ensiibackend.repos.UserAccountRepository;
import com.ensiibackend.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogRepo;
    private final UserAccountRepository userRepo;

    @Transactional
    public BlogPost create(CreateBlogPostDTO dto){

        String slug = SlugifyUtils.slugify(dto.title());
        if (blogRepo.findBySlug(slug).isPresent()) {
            throw new IllegalArgumentException("Slug already in use.");
        }

        BlogPost post = BlogPost.builder()
                .slug(slug)
                .title(dto.title())
                .excerpt(dto.excerpt())
                .content(dto.content())
                .status(dto.status())
                .coverImageUrl(dto.coverImageUrl())
                .coverImageAlt(dto.coverImageAlt())
                .author(dto.authorUserId() ==null? null : userRepo.getReferenceById(dto.authorUserId().getId()))
                .build();
        if(post.getStatus() == BlogPostStatus.PUBLISHED && post.getPublishedAt() == null){
            post.setPublishedAt(OffsetDateTime.now());
        }
        return blogRepo.save(post);
    }


    @Transactional(readOnly = true)
    public Page<BlogPost> listPublished(Pageable pageable){
        return blogRepo.findByStatusOrderByPublishedAtDesc(BlogPostStatus.PUBLISHED, pageable);
    }

    @Transactional
    public BlogPost update(String slug, UpdateBlogPostDTO dto) {
        BlogPost post = blogRepo.findBySlug(slug)
                .orElseThrow(() -> new NoSuchElementException("Blog not found"));

        // If title changed → regenerate slug
        if (!post.getTitle().equals(dto.title())) {
            String newSlug = SlugifyUtils.slugify(dto.title());
            if (blogRepo.findBySlug(newSlug).isPresent()) {
                throw new IllegalArgumentException("Slug already in use: " + newSlug);
            }
            post.setSlug(newSlug);
        }

        post.setTitle(dto.title());
        post.setExcerpt(dto.excerpt());
        post.setContent(dto.content());
        post.setStatus(dto.status());

        // Replace images if new ones provided
        post.getBlogImages().clear();
        if (dto.blogImages() != null) {
            for (var img : dto.blogImages()) {
                post.getBlogImages().add(
                        BlogImage.builder()
                                .blogPost(post)
                                .imageUrl(img.imageUrl())
                                .altText(img.altText())
                                .caption(img.caption())
                                .sortOrder(img.sortOrder())
                                .build()
                );
            }
        }

        return post;
    }

}
