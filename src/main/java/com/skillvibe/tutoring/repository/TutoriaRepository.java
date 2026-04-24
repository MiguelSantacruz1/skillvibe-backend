package com.skillvibe.tutoring.repository;

import com.skillvibe.tutoring.model.Tutoria;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TutoriaRepository extends JpaRepository<Tutoria, Long> {

    // Buscar tutorías donde el usuario sea el TUTOR
    List<Tutoria> findByTutorId(Long tutorId);

    // Buscar tutorías donde el usuario sea el ESTUDIANTE
    List<Tutoria> findByEstudianteId(Long estudianteId);
}