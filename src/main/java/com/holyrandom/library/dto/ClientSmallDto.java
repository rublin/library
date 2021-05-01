package com.holyrandom.library.dto;

import lombok.Data;

@Data
public class ClientSmallDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
