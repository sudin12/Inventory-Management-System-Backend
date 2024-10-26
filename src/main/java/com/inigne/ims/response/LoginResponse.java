package com.inigne.ims.response;

import lombok.Data;
import lombok.Getter;
@Data

public class LoginResponse {
    @Getter
    private String token;

    private long expiresIn;

    // Getters and setters...
}
