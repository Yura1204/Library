package com.catalogservice.catalogservice.controller;

import com.catalogservice.catalogservice.dto.BookDTO;
import com.catalogservice.catalogservice.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private BookService bookService;

    @Test
    public void testGetAllBooks() throws Exception {
        List<BookDTO> expectedBooks = List.of(
                new BookDTO(1L, "Book 1", "Author 1", "Description 1",
                        "Genre 1", "Publisher 1", 2024, "", 1L),
                new BookDTO(2L, "Book 2", "Author 2", "Description 2",
                        "Genre 2", "Publisher 2", 2024, "", 1L)
        );

        when(bookService.getAllBooks()).thenReturn(expectedBooks);

        ResponseEntity<String> response = restTemplate.getForEntity("/api/books", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        String expectedJson = buildExpectedJson(expectedBooks);
        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
    }

    private String buildExpectedJson(List<BookDTO> books) throws JSONException {
        JSONArray booksArray = new JSONArray();
        for (BookDTO book : books) {
            JSONObject bookJson = new JSONObject();
            bookJson.put("id", book.getId());
            bookJson.put("title", book.getTitle());
            bookJson.put("authorName", book.getAuthorName());
            bookJson.put("description", book.getDescription());
            bookJson.put("genre", book.getGenre());
            bookJson.put("publisher", book.getPublisher());
            bookJson.put("year_published", book.getYear_published());
            bookJson.put("fileUrl", book.getFileUrl());
            bookJson.put("author_id", book.getAuthor_id());
            booksArray.put(bookJson);
        }
        return booksArray.toString();
    }
}
