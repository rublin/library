package com.holyrandom.library.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class CreateUserDto {
    private String firstName;
    private String lastName;

    @NotBlank(message = "is mandatory")
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
    private String email;

    @NotBlank(message = "is mandatory")
    private String password;
}
