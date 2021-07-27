package com.holyrandom.library.mapper;

import com.holyrandom.library.dto.CreateUserDto;
import com.holyrandom.library.dto.UserDto;
import com.holyrandom.library.entity.ApplicationUser;
import lombok.Getter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
@Getter
public abstract class ApplicationUserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public abstract UserDto toDto(ApplicationUser applicationUser);

    @Mapping(target = "password", ignore = true)
    public abstract ApplicationUser toApplicationUser(CreateUserDto dto);

    @AfterMapping
    void map(@MappingTarget ApplicationUser applicationUser, CreateUserDto dto) {
        applicationUser.setPassword(passwordEncoder.encode(dto.getPassword()));
    }
}
