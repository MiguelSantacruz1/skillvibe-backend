package com.skillvibe.tutoring.dto;

// ESTA ES LA LÍNEA QUE TE FALTA:
import com.skillvibe.tutoring.model.User;
import lombok.Getter;

@Getter
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String role;
    private Double balance;

    // Aquí usas "User", por eso necesitas el import arriba
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.fullName = user.getFullName();
        this.email = user.getEmail();
        this.role = user.getRole().toString();
        this.balance = user.getBalance();
    }
}