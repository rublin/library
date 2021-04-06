package com.holyrandom.library.service;

import com.holyrandom.library.dto.ClientDto;
import com.holyrandom.library.exception.ConflictException;
import com.holyrandom.library.exception.NotFoundException;
import com.holyrandom.library.entity.Client;
import com.holyrandom.library.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public Client create(ClientDto dto) {
        Optional<Client> optional = clientRepository.findByEmailOrPhone(dto.getEmail(), dto.getPhone());

        if (optional.isPresent()) {
            throw new ConflictException("Client already exists");
        }

        Client client = new Client(dto);

        Client saved = clientRepository.save(client);

        log.info("Client {} created", saved);

        return saved;
    }

    public Page<Client> getAll(String query, Pageable pageable) {
        if (query != null) {
            return clientRepository.findByQuery("%" + query.toLowerCase() + "%", pageable);
        }

        return clientRepository.findAll(pageable);
    }

    public Client get(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found"));
    }

    public Client update(Long id, ClientDto dto) {
        Client client = get(id);
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setEmail(dto.getEmail());
        client.setPhone(dto.getPhone());

        return clientRepository.save(client);
    }

    public void delete(Long id) {
        Client client = get(id);
        clientRepository.delete(client);

        log.info("Client {} successfully deleted", client);
    }
}
