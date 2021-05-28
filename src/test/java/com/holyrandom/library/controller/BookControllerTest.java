package com.holyrandom.library.controller;

import com.holyrandom.library.AbstractControllerTest;
import com.holyrandom.library.dto.BookDto;
import com.holyrandom.library.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends AbstractControllerTest {

    @Test
    void createBook() throws Exception {
        BookDto testBook = mockBook();

        MvcResult mvcResult = createBook(testBook)
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Book bookResult = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Book.class);
        assertThat(bookResult.getId(), notNullValue());
        assertThat(bookResult.getName(), equalTo(testBook.getName()));
        assertThat(bookResult.getAuthor(), equalTo(testBook.getAuthor()));
        assertThat(bookResult.getDescription(), equalTo(testBook.getDescription()));
        assertThat(bookResult.getPublisher(), equalTo(testBook.getPublisher()));
        assertThat(bookResult.getYear(), equalTo(testBook.getYear()));
        assertThat(bookResult.getIsbn(), equalTo(testBook.getIsbn()));
    }

    @Test
    void findBook() throws Exception {
        BookDto testBook = mockBook();
        createBook(testBook)
                .andDo(print())
                .andExpect(status().isCreated());

        mockMvc.perform(get("/book").param("page", "0").param("size", "1").param("query", "qwet"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", empty()));

        mockMvc.perform(get("/book").param("page", "0").param("size", "1").param("query", "author"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[*].bookHistories").doesNotExist());
    }

    @Test
    void createBookValidation() throws Exception {
        BookDto testBook = mockBook();
        testBook.setName(null);
        testBook.setAuthor("");

        createBook(testBook)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder("author is mandatory", "name is mandatory")));

        testBook = mockBook();
        testBook.setIsbn("wrong format");

        createBook(testBook)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", equalTo(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder("isbn must match \"^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$\"")));

    }

    @Test
    void getNotExistingBook() throws Exception {
        mockMvc.perform(get("/book/321"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("status", equalTo(HttpStatus.NOT_FOUND.name())))
                .andExpect(jsonPath("errors", contains("Book not found")));
    }

}