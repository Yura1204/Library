package com.storageservice.storageservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "author_input")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long author_id;
    private String authorname;
    private String biography;
}
