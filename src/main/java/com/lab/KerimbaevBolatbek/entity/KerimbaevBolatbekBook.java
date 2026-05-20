package com.lab.KerimbaevBolatbek.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"authors", "loans", "reviews"})
@EqualsAndHashCode(of = "id")
public class KerimbaevBolatbekBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(length = 2000)
    private String description;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(length = 100)
    private String publisher;

    @Column(name = "total_copies", nullable = false)
    @Builder.Default
    private Integer totalCopies = 1;

    @Column(name = "available_copies", nullable = false)
    @Builder.Default
    private Integer availableCopies = 1;

    @Column(name = "cover_image_url", length = 500)
    private String coverImageUrl;

    @Column(name = "file_url", length = 500)
    private String fileUrl;

    // ===== Many-to-One: many books → one category =====
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private KerimbaevBolatbekCategory category;

    // ===== Many-to-Many: book ↔ authors =====
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Builder.Default
    private Set<KerimbaevBolatbekAuthor> authors = new HashSet<>();

    // ===== One-to-Many: book → loans =====
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<KerimbaevBolatbekLoan> loans = new HashSet<>();

    // ===== One-to-Many: book → reviews =====
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private Set<KerimbaevBolatbekReview> reviews = new HashSet<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
