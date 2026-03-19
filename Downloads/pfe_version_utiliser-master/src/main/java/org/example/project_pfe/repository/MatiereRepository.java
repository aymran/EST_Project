package org.example.project_pfe.repository;

import org.example.project_pfe.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatiereRepository extends JpaRepository<Matiere, Integer> {

    List<Matiere> findByFiliere_IdFiliere(Integer filiereId);

}