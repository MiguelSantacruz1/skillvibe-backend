package com.skillvibe.tutoring.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutor")
public class TutorController {

    @GetMapping("/panel")
    @PreAuthorize("hasRole('TUTOR')") //  El candado para proteger la finca
    public ResponseEntity<String> zonaPrivada() {
        return ResponseEntity.ok("Bienvenido al panel de profesores, Andres. Este mensaje solo lo ven los Tutores.");
    }
}