package com.brujastore.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String contra;
}
