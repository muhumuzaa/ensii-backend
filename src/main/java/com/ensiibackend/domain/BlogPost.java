package com.ensiibackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="blog_posts", indexes ={
        @Index(name ="idx_blog_author_user", columnList ="author_user_id")
})
@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class BlogPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(max=160)
    @Column(nullable=false, unique=true, length =160)
    private String slug;

    @NotBlank @Size(max=128)
    @Column(nullable=false, length =128)
    private String title;

    @Column(columnDefinition="text")
    private String excerpt;

    @NotBlank
    @Column(columnDefinition="text", nullable=false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private BlogPostStatus status = BlogPostStatus.DRAFT;

    @Column(name="published_at")
    private OffsetDateTime publishedAt;

    @Size(max=512)
    @Column(name="cover_image_url", length = 512)
    private String coverImageUrl;

    @Size(max=160)
    @Column(name="cover_image_alt", length = 160)
    private String coverImageAlt;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="author_user_id")
    private UserAccount author;

    @Builder.Default
    @OneToMany(mappedBy="post")
    @JoinColumn(name="blog_images")
    private List<BlogImage> blogImages = new ArrayList<>();

    @Column(name="created_at", nullable =false)
    private OffsetDateTime createdAt;

    @Column(name="updated_at", nullable =false)
    private OffsetDateTime updatedAt;

    @PrePersist void onCreate(){ createdAt = updatedAt = OffsetDateTime.now();}
    @PreUpdate void onUpdate(){ updatedAt = OffsetDateTime.now();}

}
