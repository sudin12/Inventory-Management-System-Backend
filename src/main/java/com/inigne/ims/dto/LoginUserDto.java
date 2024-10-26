package com.inigne.ims.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserDto {
    private String email;

    private String password;
}
