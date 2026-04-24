package com.skillvibe.tutoring.service;

import com.skillvibe.tutoring.model.Tutoria;
import com.skillvibe.tutoring.model.User;
import com.skillvibe.tutoring.repository.TutoriaRepository;
import com.skillvibe.tutoring.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class TutoriaService {

    private final TutoriaRepository tutoriaRepository;
    private final UserRepository userRepository;

    public TutoriaService(TutoriaRepository tutoriaRepository, UserRepository userRepository) {
        this.tutoriaRepository = tutoriaRepository;
        this.userRepository = userRepository;
    }

    // 1. Programar y Cobrar al Estudiante
    @Transactional
    public Tutoria guardarTutoria(Tutoria tutoria) {
        User estudiante = userRepository.findById(tutoria.getEstudiante().getId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        if (estudiante.getBalance() < tutoria.getPrecio()) {
            throw new RuntimeException("Saldo insuficiente. ¡Recarga tu cuenta, Andres!");
        }

        // Descontar saldo al alumno
        estudiante.setBalance(estudiante.getBalance() - tutoria.getPrecio());
        userRepository.save(estudiante);

        // Configuración inicial de la clase
        String roomName = "SkillVibe-" + UUID.randomUUID().toString().substring(0, 8);
        tutoria.setMeetingLink("https://meet.jit.si/" + roomName);
        tutoria.setEstado("PROGRAMADA");

        return tutoriaRepository.save(tutoria);
    }

    // 2. BUSCAR ACTIVIDAD (Para el Tablero)
    public List<Tutoria> listarPorUsuario(Long userId) {
        List<Tutoria> comoTutor = tutoriaRepository.findByTutorId(userId);
        List<Tutoria> comoEstudiante = tutoriaRepository.findByEstudianteId(userId);
        comoTutor.addAll(comoEstudiante);
        return comoTutor;
    }

    // 3. ✨ EL MÉTODO QUE FALTABA: FINALIZAR Y PAGAR AL TUTOR
    @Transactional
    public Tutoria finalizarTutoria(Long tutoriaId) {
        // Buscamos la tutoría
        Tutoria tutoria = tutoriaRepository.findById(tutoriaId)
                .orElseThrow(() -> new RuntimeException("Tutoría no encontrada con ID: " + tutoriaId));

        // Validamos que no esté ya finalizada
        if ("FINALIZADA".equals(tutoria.getEstado())) {
            throw new RuntimeException("Esta clase ya fue pagada y finalizada.");
        }

        // 1. Cambiamos el estado
        tutoria.setEstado("FINALIZADA");

        // 2. Le sumamos la plata al balance del Tutor
        User tutor = tutoria.getTutor();
        tutor.setBalance(tutor.getBalance() + tutoria.getPrecio());
        userRepository.save(tutor);

        System.out.println("-----> CLASE FINALIZADA. PAGO REALIZADO AL TUTOR: " + tutor.getFullName());

        return tutoriaRepository.save(tutoria);
    }
}