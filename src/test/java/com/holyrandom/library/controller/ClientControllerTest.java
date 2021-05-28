package com.holyrandom.library.controller;

import com.holyrandom.library.AbstractControllerTest;
import com.holyrandom.library.dto.ClientDto;
import com.holyrandom.library.entity.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientControllerTest extends AbstractControllerTest {

    @AfterEach
    void clearAfter() {
        clientRepository.deleteAll();
    }

    @Test
    void createClient() throws Exception {
        ClientDto testDto = mockClient();
        createAndAssert(testDto);
    }

    @Test
    void getAllClients() throws Exception {
        ClientDto testDto1 = mockClient();
        ClientDto testDto2 = new ClientDto();
        testDto2.setFirstName("Bohdan");
        testDto2.setLastName("Khmelnytskyi");
        testDto2.setEmail("bohdanLyahamNavalyav@gmail.com");
        testDto2.setPhone("+380981234567");

        createAndAssert(testDto2);
        createAndAssert(testDto1);

//        get all clients page
        mockMvc.perform(get("/client")
                .param("page", "0")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content.[*].firstName",
                        containsInAnyOrder(testDto1.getFirstName(), testDto2.getFirstName())))
                .andExpect(jsonPath("$.content.[*].phone",
                        containsInAnyOrder(testDto1.getPhone(), testDto2.getPhone())))
                .andExpect(jsonPath("$.content.[*].bookInUses").doesNotExist());

//        get page with filter by name
        mockMvc.perform(get("/client")
                .param("page", "0")
                .param("size", "10")
                .param("query", testDto1.getFirstName()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].firstName", is(testDto1.getFirstName())));

//        get page with filter by phone
        mockMvc.perform(get("/client")
                .param("page", "0")
                .param("size", "10")
                .param("query", testDto2.getPhone()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].phone", is(testDto2.getPhone())));
    }

    @Test
    void getClient() throws Exception {
        ClientDto testDto1 = mockClient();
        Client client = createAndAssert(testDto1);

        mockMvc.perform(get("/client/" + client.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(client.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(testDto1.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(testDto1.getLastName())))
                .andExpect(jsonPath("$.email", is(testDto1.getEmail())))
                .andExpect(jsonPath("$.phone", is(testDto1.getPhone())));
    }

    @Test
    void editClient() throws Exception {
        ClientDto dto = mockClient();
        Client client = createAndAssert(dto);

        dto.setFirstName("Mykola");

        mockMvc.perform(put("/client/" + client.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(client.getId().intValue())))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())));
    }

    @Test
    void deleteClient() throws Exception {
        ClientDto dto = mockClient();
        Client client = createAndAssert(dto);

        mockMvc.perform(get("/client/" + client.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/client/" + client.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/client/" + client.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void createClient_expectBadRequest_whenValidationFailed() throws Exception {
        ClientDto dto = new ClientDto();
        dto.setFirstName(null);
        dto.setLastName("");
        dto.setEmail("wrong format");
        dto.setPhone("099-123-11-22");

        createClient(dto)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(HttpStatus.BAD_REQUEST.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "firstName is mandatory",
                        "lastName is mandatory",
                        "email must match \"^\\S+@\\S+\\.\\S+$\"",
                        "phone must match \"^\\+380\\d{9}$\"")));
    }

    @Test
    void createClient_expectConflict_whenClientAlreadyExists() throws Exception {
        ClientDto dto = mockClient();
        createAndAssert(dto);

        createClient(dto)
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status", is(HttpStatus.CONFLICT.name())))
                .andExpect(jsonPath("$.errors", containsInAnyOrder("Client already exists")));
    }

    private Client createAndAssert(ClientDto dto) throws Exception {
        MvcResult mvcResult = createClient(dto)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", not(nullValue())))
                .andExpect(jsonPath("$.firstName", is(dto.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(dto.getLastName())))
                .andExpect(jsonPath("$.email", is(dto.getEmail())))
                .andExpect(jsonPath("$.phone", is(dto.getPhone())))
                .andReturn();

        return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Client.class);
    }
}