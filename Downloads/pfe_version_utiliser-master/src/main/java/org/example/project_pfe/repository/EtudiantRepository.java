package org.example.project_pfe.repository;

import java.util.List;
import java.util.Optional;

import org.example.project_pfe.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Integer> {

    Optional<Etudiant> findByEmail(String email);

    Optional<Etudiant> findByNumeroInscription(String numeroInscription);

    boolean existsByEmail(String email);

    boolean existsByNumeroInscription(String numeroInscription);

    List<Etudiant> findByFiliere_IdFiliere(Integer filiereId);
}