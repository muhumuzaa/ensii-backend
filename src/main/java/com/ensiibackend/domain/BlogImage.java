package com.ensiibackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name="blog_images", uniqueConstraints = {
        @UniqueConstraint(name="uq_post_image_order", columnNames ={"blog_id", "sort_order"})
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogImage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =FetchType.LAZY, optional=false)
    @JoinColumn(name="blog_id", nullable =false)
    private BlogPost blogPost;

    @Column(name ="image_url", nullable=false, length=512)
    @NotBlank @Size(max=512)
    private String imageUrl;

    @Column(name ="alt_text", length=160)
    @NotBlank @Size(max=160)
    private String altText;

    @Column(columnDefinition ="text")
    private String caption;

    @Column(name="sort_order", nullable=false)
    private Integer sortOrder =0;

    @Column(name="created_at", nullable =false)
    private OffsetDateTime createdAt;

    @Column(name="updated_at", nullable =false)
    private OffsetDateTime updatedAt;

    @PrePersist void onCreate(){ createdAt = updatedAt = OffsetDateTime.now();}
    @PreUpdate void onUpdate(){ updatedAt = OffsetDateTime.now();}

}
