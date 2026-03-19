package org.example.project_pfe.repository;

import org.example.project_pfe.model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Integer> {

    Optional<Enseignant> findByEmail(String email);

    List<Enseignant> findByGrade(String grade);

    List<Enseignant> findBySpecialite(String specialite);

    boolean existsByEmail(String email);

}