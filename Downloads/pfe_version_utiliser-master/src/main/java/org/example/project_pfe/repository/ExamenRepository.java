package org.example.project_pfe.repository;

import org.example.project_pfe.model.Examen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamenRepository extends JpaRepository<Examen, Integer> {

    List<Examen> findByMatiere_IdMatiere(Integer idMatiere);
}