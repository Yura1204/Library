package com.catalogservice.catalogservice.dto;

import com.catalogservice.catalogservice.model.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String authorName;
    private String description;
    private String genre;
    private String publisher;
    private int year_published;
    private String fileUrl;
    private Long author_id;
}
