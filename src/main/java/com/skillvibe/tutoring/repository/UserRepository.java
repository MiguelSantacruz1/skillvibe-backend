package com.skillvibe.tutoring.repository;

import com.skillvibe.tutoring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Busca un usuario por su email para validaciones de login/registro
    Optional<User> findByEmail(String email);
}