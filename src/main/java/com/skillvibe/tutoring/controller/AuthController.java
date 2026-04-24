package com.skillvibe.tutoring.controller;

import com.skillvibe.tutoring.dto.AuthResponseDTO;
import com.skillvibe.tutoring.dto.LoginRequest;
import com.skillvibe.tutoring.dto.UserResponseDTO;
import com.skillvibe.tutoring.model.User;
import com.skillvibe.tutoring.security.JwtService;
import com.skillvibe.tutoring.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
// 1. ELIMINADO: @CrossOrigin(origins = "*") -> Ya se maneja en SecurityConfig
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(new UserResponseDTO(newUser));
        } catch (Exception e) {
            e.printStackTrace(); // Crucial para ver el error en los logs de Render
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 1. Validamos credenciales
            User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

            // 2. Generamos el Token
            String token = jwtService.generateToken(user);

            // 3. Devolvemos respuesta completa
            return ResponseEntity.ok(new AuthResponseDTO(token, new UserResponseDTO(user)));
        } catch (Exception e) {
            e.printStackTrace(); // Si el login falla, veremos por qué en la consola
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id) {
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(new UserResponseDTO(user));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/perfil-estudiante")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> zonaEstudiantes() {
        return ResponseEntity.ok("¡Acceso concedido! Hola Andres, este es tu panel de estudiante.");
    }
}