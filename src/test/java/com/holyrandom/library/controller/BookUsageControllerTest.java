package com.holyrandom.library.controller;

import com.holyrandom.library.AbstractControllerTest;
import com.holyrandom.library.entity.Book;
import com.holyrandom.library.entity.Client;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookUsageControllerTest extends AbstractControllerTest {
    private Client client;
    private Book book;

    @BeforeAll
    @SneakyThrows
    public void init() {
        var clientResult = createClient(mockClient()).andReturn();
        client = objectMapper.readValue(clientResult.getResponse().getContentAsString(), Client.class);

        var bookResult = createBook(mockBook()).andReturn();
        book = objectMapper.readValue(bookResult.getResponse().getContentAsString(), Book.class);
    }

    @Test
    void expired() throws Exception {
        mockMvc.perform(put("/usage/client/{clientId}/book/{bookId}", client.getId(), book.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        when(timeService.currentDate()).thenReturn(LocalDate.now());
        mockMvc.perform(get("/usage/expired"))
                .andDo(print())
                .andExpect(jsonPath("$", empty()));

        when(timeService.currentDate()).thenReturn(LocalDate.now().plusDays(5));
        mockMvc.perform(get("/usage/expired"))
                .andDo(print())
                .andExpect(jsonPath("$", empty()));

        when(timeService.currentDate()).thenReturn(LocalDate.now().plusDays(15));
        mockMvc.perform(get("/usage/expired"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].book.id", equalTo(book.getId().intValue())))
                .andExpect(jsonPath("$.[0].expiredDays", equalTo(1)));

        when(timeService.currentDate()).thenReturn(LocalDate.now().plusDays(20));
        mockMvc.perform(get("/usage/expired"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].book.id", equalTo(book.getId().intValue())))
                .andExpect(jsonPath("$.[0].expiredDays", equalTo(6)));
    }
}