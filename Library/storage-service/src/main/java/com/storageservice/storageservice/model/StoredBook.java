package com.storageservice.storageservice.model;

import com.catalogservice.catalogservice.model.Author;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pub_books")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoredBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;
    private String title;
    private String description;
    private String genre;
    private String publisher;
    private int year_published;
    private String fileUrl;

    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] book_content;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private StoredAuthor author;
}
