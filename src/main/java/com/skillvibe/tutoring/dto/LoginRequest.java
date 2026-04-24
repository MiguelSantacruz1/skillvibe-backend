package com.skillvibe.tutoring.dto;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // <--- ESTA ES LA CLAVE: Crea el constructor vacío
@AllArgsConstructor // Crea el constructor con datos
public class LoginRequest {
    private String email;
    private String password;
}