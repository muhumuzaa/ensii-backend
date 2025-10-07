package com.ensiibackend.repos;

import com.ensiibackend.domain.BlogPost;
import com.ensiibackend.domain.BlogPostStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
    Optional<BlogPost> findBySlug(String slug);
    Page<BlogPost> findByStatusOrderByPublishedAtDesc(BlogPostStatus status, Pageable pageable);
}
