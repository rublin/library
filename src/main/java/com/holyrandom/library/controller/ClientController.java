package com.holyrandom.library.controller;

import com.holyrandom.library.dto.ClientDetailsDto;
import com.holyrandom.library.dto.ClientDto;
import com.holyrandom.library.dto.ClientSmallDto;
import com.holyrandom.library.entity.Client;
import com.holyrandom.library.mapper.ClientMapper;
import com.holyrandom.library.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping("/client")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Client createClient(@Valid @RequestBody ClientDto dto) {
        return clientService.create(dto);
    }

    @GetMapping("/client")
    public Page<ClientSmallDto> getAllClients(@RequestParam Integer page,
                                              @RequestParam Integer size,
                                              @RequestParam(required = false) String query) {
        Pageable pageable = PageRequest.of(page, size);
        final Page<Client> clients = clientService.getAll(query, pageable);

        final List<ClientSmallDto> dtos = clients.get()
                .map(clientMapper::toSmall)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, clients.getTotalElements());
    }

    @GetMapping("/client/{id}")
    public ClientDetailsDto getClient(@PathVariable Long id) {
        return clientMapper.toDetails(clientService.get(id));
    }

    @PutMapping("/client/{id}")
    public ClientDetailsDto editClient(@PathVariable Long id,
                                       @Valid @RequestBody ClientDto dto) {
        return clientMapper.toDetails(clientService.update(id, dto));
    }

    @DeleteMapping("/client/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.delete(id);
    }
}
