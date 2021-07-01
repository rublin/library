package com.holyrandom.library.performance;

import com.holyrandom.library.AbstractControllerTest;
import com.holyrandom.library.entity.Book;
import com.holyrandom.library.entity.Client;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookUsagePerformanceTest extends AbstractControllerTest {
    private final List<Client> clients = new ArrayList<>();
    private final List<Book> books = new ArrayList<>();

    @BeforeAll
    @SneakyThrows
    public void init() {
        var clientResults = createClients(100);
        var bookResults = createBooks(1000);

        for (String client : clientResults) {
            clients.add(objectMapper.readValue(client, Client.class));
        }

        for (String book : bookResults) {
            books.add(objectMapper.readValue(book, Book.class));
        }

        for (int i = 0; i < clients.size(); i++) {
            var client = clients.get(i);

            for (int j = i * 10; j < i * 10 + 10; j++) {
                var book = books.get(j);
                mockMvc.perform(put("/usage/client/{clientId}/book/{bookId}", client.getId(), book.getId()))
                        .andExpect(status().isOk());
            }
        }
    }

    @AfterAll
    public void clean() {
        clientRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void n_plus_one_inExpired() throws Exception {
        when(timeService.currentDate()).thenReturn(LocalDate.now().plusDays(15));
        mockMvc.perform(get("/usage/expired"))
                .andExpect(jsonPath("$", hasSize(1000)));
    }
}
