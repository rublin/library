package com.holyrandom.library.dto;

import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private boolean locked;
}
