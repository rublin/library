package com.holyrandom.library;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks() {
    }

    @Test
    void getBookById() {
    }

    @Test
    void createBook() throws Exception {
        Book testBook = new Book();
        testBook.setName("Test book name");
        testBook.setAuthor("Test book author");
        testBook.setYear(2020);
        testBook.setPublisher("Test publisher");
        testBook.setDescription("Test description");
        testBook.setIsbn("test isbn");

        String json = objectMapper.writeValueAsString(testBook);
        MvcResult mvcResult = mockMvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        Book response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Book.class);
        assertThat(response.getId(), notNullValue());
        assertThat(response.getName(), equalTo(testBook.getName()));
    }

    @Test
    void editBook() {
    }

    @Test
    void deleteBook() {
    }
}