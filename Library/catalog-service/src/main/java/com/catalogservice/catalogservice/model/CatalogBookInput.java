package com.catalogservice.catalogservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CatalogBookInput {
    private String title;
    private String authorname;
    private String description;
    private String genre;
    private String publisher;
    private int yearPublished;
    private byte[] bookContent;
}
