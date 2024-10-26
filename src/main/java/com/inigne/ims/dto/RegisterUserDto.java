package com.inigne.ims.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;

}