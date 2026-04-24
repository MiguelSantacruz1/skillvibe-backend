package com.skillvibe.tutoring.controller;

import com.skillvibe.tutoring.model.Tutoria;
import com.skillvibe.tutoring.service.TutoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutorias")
@Tag(name = "Tutorías", description = "Endpoints para la gestión de clases y pagos de SkillVibe")
@SecurityRequirement(name = "BearerAuth") // Para que Swagger pida el Token
public class TutoriaController {

    private final TutoriaService tutoriaService;

    public TutoriaController(TutoriaService tutoriaService) {
        this.tutoriaService = tutoriaService;
    }

    @Operation(summary = "Programar una nueva clase", description = "Solo accesible para usuarios con rol TUTOR. Descuenta el precio del balance del estudiante.")
    @PostMapping("/programar")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<Tutoria> crearTutoria(@RequestBody Tutoria tutoria) {
        return ResponseEntity.ok(tutoriaService.guardarTutoria(tutoria));
    }

    @Operation(summary = "Ver el tablero de actividades", description = "Muestra todas las tutorías asociadas a un usuario (como tutor o como estudiante).")
    @GetMapping("/mi-tablero/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Tutoria>> obtenerTablero(@PathVariable Long userId) {
        return ResponseEntity.ok(tutoriaService.listarPorUsuario(userId));
    }

    @Operation(summary = "Finalizar clase y pagar al tutor", description = "Cambia el estado a FINALIZADA y suma el valor de la clase al balance del tutor.")
    @PutMapping("/{id}/finalizar")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<Tutoria> finalizar(@PathVariable Long id) {
        return ResponseEntity.ok(tutoriaService.finalizarTutoria(id));
    }
}