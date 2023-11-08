package com.catalogservice.catalogservice.model;

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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_id;
    private String title;
    private String authorname;
    private String description;
    private String genre;
    private String publisher;
    private int year_published;
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author_id;
}
