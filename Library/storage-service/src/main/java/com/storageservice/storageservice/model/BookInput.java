package com.storageservice.storageservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_input")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long book_input_id;
    private String title;
    private String authorname;
    private String description;
    private String genre;
    private String publisher;
    private int yearPublished;
    private byte[] bookContent;
}
