package com.holyrandom.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holyrandom.library.dto.BookDto;
import com.holyrandom.library.dto.ClientDto;
import com.holyrandom.library.repository.BookRepository;
import com.holyrandom.library.repository.ClientRepository;
import com.holyrandom.library.service.TimeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractControllerTest {

    private static final String ALFABET = "abcdefghijklmnopqrstuvwxyz";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected ClientRepository clientRepository;
    @Autowired
    protected BookRepository bookRepository;

    @MockBean
    protected TimeService timeService;

    @SneakyThrows
    protected ResultActions createClient(ClientDto dto) {
        return mockMvc.perform(post("/client")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
    }

    @SneakyThrows
    protected List<String> createClients(int amount) {
        var result = new ArrayList<String>();

        for (int i = 0; i < amount; i++) {
            var dto = new ClientDto();
            dto.setFirstName(randomString());
            dto.setLastName(randomString());
            dto.setEmail(dto.getFirstName() + "." + dto.getLastName() + "@gmail.com");
            dto.setPhone("+380" + randomInt(100000000, 999999999));

            var client = createClient(dto).andReturn();
            result.add(client.getResponse().getContentAsString());
        }

        return result;
    }

    protected ClientDto mockClient() {
        ClientDto dto = new ClientDto();
        dto.setFirstName("Ivan");
        dto.setLastName("Mazepa");
        dto.setEmail("ivanMoskalivNaduryv@gmail.com");
        dto.setPhone("+380501234567");

        return dto;
    }

    @SneakyThrows
    protected ResultActions createBook(BookDto dto) {
        return mockMvc.perform(post("/book").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));
    }

    @SneakyThrows
    protected List<String> createBooks(int amount) {
        var result = new ArrayList<String>();

        for (int i = 0; i < amount; i++) {
            var dto = new BookDto();
            dto.setName(randomString());
            dto.setAuthor(randomString());
            dto.setPublisher(randomString());
            dto.setYear(randomInt(1900, 2021));
            dto.setIsbn(String.format("%d-%d-%d-%d-%d", randomInt(100, 999), randomInt(100, 999), randomInt(1000, 9999), randomInt(10, 99), randomInt(1, 9)));

            var book = createBook(dto).andReturn();
            result.add(book.getResponse().getContentAsString());
        }

        return result;
    }

    protected BookDto mockBook() {
        BookDto testBook = new BookDto();
        testBook.setName("Test name");
        testBook.setDescription("Test description");
        testBook.setAuthor("Test author");
        testBook.setPublisher("Test publisher");
        testBook.setYear(2020);
        testBook.setIsbn("978-617-7866-64-9");

        return testBook;
    }

    private int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private String randomString() {
        var length = randomInt(5, 10);
        var builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            var position = randomInt(0, ALFABET.length() - 1);
            var c = i == 0 ? ALFABET.toUpperCase().charAt(position) : ALFABET.charAt(position);
            builder.append(c);
        }

        return builder.toString();
    }
}
