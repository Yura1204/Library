package com.storageservice.storageservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "pub_authors")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoredAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long author_id;

    private String authorname;
    private String biography;

    @OneToMany
    private List<StoredBook> storedBooks;
}