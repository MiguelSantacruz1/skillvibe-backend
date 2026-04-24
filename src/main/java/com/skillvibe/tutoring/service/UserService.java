package com.skillvibe.tutoring.service;

import com.skillvibe.tutoring.model.User;
import com.skillvibe.tutoring.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }
    private final PasswordEncoder passwordEncoder; // Inyectamos el encriptador

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) throws Exception {
        // 1. Validar que el email no exista
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("Email already exists");
        }

        // 2. ENCRIPTAR la contraseña antes de guardar
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }
    // Verifica si las credenciales son correctas
    public User login(String email, String password) throws Exception {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Comparamos la clave escrita con la encriptada en la base de datos
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        return user; // Si todo está bien, devolvemos el usuario
    }
}