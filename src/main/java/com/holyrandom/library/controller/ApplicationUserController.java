package com.holyrandom.library.controller;

import com.holyrandom.library.dto.CreateUserDto;
import com.holyrandom.library.dto.UserDto;
import com.holyrandom.library.mapper.ApplicationUserMapper;
import com.holyrandom.library.service.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApplicationUserController {

    private final ApplicationUserService applicationUserService;
    private final ApplicationUserMapper applicationUserMapper;

    @PostMapping("/users")
    public UserDto create(@RequestBody @Valid CreateUserDto createUserDto) {
        var user = applicationUserService.createUser(applicationUserMapper.toApplicationUser(createUserDto));
        return applicationUserMapper.toDto(user);
    }

    @GetMapping("/users")
    public Page<UserDto> getAll(@RequestParam Integer page,
                                @RequestParam Integer size) {
        var pageable = PageRequest.of(page, size);
        var applicationUsers = applicationUserService.getAll(pageable);

        var dtos = applicationUsers.get()
                .map(applicationUserMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, applicationUsers.getTotalElements());
    }

    @GetMapping("/users/{id}")
    public UserDto getById(@PathVariable Long id) {
        return applicationUserMapper.toDto(applicationUserService.get(id));
    }

    @PutMapping("/users/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid CreateUserDto dto) {
        return applicationUserMapper.toDto(applicationUserService.update(id, applicationUserMapper.toApplicationUser(dto)));
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Long id) {
        applicationUserService.delete(id);
    }
}
